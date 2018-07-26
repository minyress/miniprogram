package cn.org.miny.util;

import cn.org.miny.common.Configure;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 *
 * Created by limingyang on 2018/7/19.
 */
public class TokenUtil {

    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * 创建token
     * @param content 明文
     * @return
     */
    public static String createToken(String content) {
        Date now = new Date();
        Date expDate = new Date(System.currentTimeMillis() + Configure.TOKEN_EXP);
        String token = Jwts.builder()
                .setSubject(content) //设置明文
                .signWith(SignatureAlgorithm.HS256, Configure.TOKEN_SECRET)
                .setExpiration(expDate) //过期时间
                .setIssuedAt(now) //签发时间
                .compact();
        return token;
    }


    /**
     * 解密token
     * @param token
     * @return content
     */
    public static String parseToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(Configure.TOKEN_SECRET).parseClaimsJws(token).getBody();
            return claims.getSubject();
        }
//        catch (ExpiredJwtException e) { //超时
//            logger.error(e.getMessage());
//        } catch (SignatureException e) { //签名错误
//            logger.error(e.getMessage());
//        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
