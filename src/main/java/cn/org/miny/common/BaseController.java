package cn.org.miny.common;

import cn.org.miny.model.MiniProgramUser;
import cn.org.miny.service.MiniService;
import cn.org.miny.util.RedisUtil;
import cn.org.miny.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础Controller
 * Created by limingyang on 2018/7/18.
 */
public abstract class BaseController {

    /**
     *
     */
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected MiniService miniService;

    /**
     * 返回错误结果
     * @return
     */
    public Result fail() {
        return Result.fail();
    }

    /**
     * 返回错误结果
     * @param code 状态码
     * @param desc 状态码描述
     * @return
     */
    public Result fail(String code, String desc) {
        return Result.fail(code, desc);
    }

    /**
     * 返回正确结果
     * @return
     */
    public Result success() {
        return Result.success();
    }

    /**
     * 返回正确结果
     * @param code 状态码
     * @param desc 状态码描述
     * @return
     */
    public Result success(String code, String desc) {
        return Result.success(code, desc);
    }

    /**
     * 得到当前登录小程序用户
     * @return
     */
    public MiniProgramUser getMiniProgramUser() throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new Exception("登录信息为空");
        }
        String sessionKey = null;
        try {
            sessionKey = TokenUtil.parseToken(token);
        } catch (ExpiredJwtException e) { //超时
            logger.info(e.getMessage());
            throw new Exception("登录超时");
        } catch (SignatureException e) { //签名错误
            logger.info(e.getMessage());
            throw new Exception("非法登录授权");
        } catch (Exception e) { //签名错误
            logger.info(e.getMessage());
            throw new Exception("登录超时");
        }
        String openid = RedisUtil.get(sessionKey);
        if (StringUtils.isEmpty(openid)) {
            throw new Exception("登录超时");
        }
        MiniProgramUser user = this.miniService.findMiniUserByOpenid(openid);
        if (ObjectUtils.isEmpty(user)) {
            throw new Exception("非法登录授权");
        }
        return user;
    }
}
