package cn.org.miny.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * SpringMVC Controller异常的统一处理
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     *
     */
    private static final transient Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @Autowired
    private HttpServletRequest request;

    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public Result handler(Throwable e) {
        logger.error(e.getMessage(), e);
        return Result.fail(e.getMessage());
    }
}
