package cn.org.miny.filter;

import cn.org.miny.common.Result;
import cn.org.miny.model.MiniProgramUser;
import cn.org.miny.service.MiniService;
import cn.org.miny.util.RedisUtil;
import cn.org.miny.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 小程序登录拦截
 * Created by limingyang on 2018/7/19.
 */
public class LoginFilter extends OncePerRequestFilter {

    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath().toLowerCase();
        if (path.endsWith("/login/jscode2session")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            error("111", "token不能为空", response);
            return;
        }
        String sessionKey = null;
        try {
            sessionKey = TokenUtil.parseToken(token);
        } catch (ExpiredJwtException e) { //超时
            logger.info(e.getMessage());
            error("111", "token过期", response);
            return;
        } catch (SignatureException e) { //签名错误
            logger.info(e.getMessage());
            error("111", "token不合法", response);
            return;
        } catch (Exception e) { //签名错误
            logger.info(e.getMessage());
            error("111", "token不合法", response);
            return;
        }
        String openid = RedisUtil.get(sessionKey);
        if (StringUtils.isEmpty(openid)) {
            error("111", "登录超时,请重新登录！", response);
            return;
        }
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        MiniService miniService = applicationContext.getBean("miniService", MiniService.class);
        MiniProgramUser miniProgramUser = miniService.findMiniUserByOpenid(openid);
        if (ObjectUtils.isEmpty(miniProgramUser)) {
            error("111", "未登录小程序！", response);
            return;
        }
        filterChain.doFilter(request, response);
    }


    private void error(String code, String desc, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(Result.fail(code, desc)));
    }

}
