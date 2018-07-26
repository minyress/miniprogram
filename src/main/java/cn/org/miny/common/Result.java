package cn.org.miny.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Controller 返回类
 * Created by limingyang on 2018/7/18.
 */
@Data
public class Result implements Serializable {

    /**
     *
     */
    protected static final Logger logger = LoggerFactory.getLogger(Result.class);

    /**
     * 返回码
     */
    @ApiModelProperty(value = "返回码", position = 0)
    protected String code;

    /**
     * 返回码描述
     */
    @ApiModelProperty(value = "返回码描述", position = 1)
    protected String desc;

    Result() {
        this.code = SUCCESS_CODE;
        this.desc = SUCCESS_MSG;
    }

    Result(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 正确状态码
     */
    protected static final String SUCCESS_CODE = "0";

    /**
     * 错误状态码
     */
    protected static final String FAIL_CODE = "-1";

    /**
     *
     */
    protected static final String SUCCESS_MSG = "操作成功";

    /**
     *
     */
    protected static final String FAIL_MSG = "系统出现异常，请稍后再试！";

    /**
     * 生成Result
     * @param code 状态码
     * @param desc 状态码描述
     * @return
     */
    private static Result generateResult(String code, String desc) {
        return new Result(StringUtils.isEmpty(code) ? SUCCESS_CODE : code, StringUtils.isEmpty(desc) ? SUCCESS_MSG : desc);
    }

    /**
     * 返回默认信息的错误Result
     * @return
     */
    public static Result fail() {
        return generateResult(FAIL_CODE, FAIL_MSG);
    }

    /**
     * 返回错误Result,并指定信息
     * @param desc 状态码描述
     * @return
     */
    public static Result fail(String desc) {
        return generateResult(FAIL_CODE, desc);
    }

    /**
     * 返回错误Result,并指定信息
     * @param code 状态码
     * @param desc 状态码描述
     * @return
     */
    public static Result fail(String code, String desc) {
        return generateResult(code, desc);
    }

    /**
     * 返回默认信息的正确Result
     * @return
     */
    public static Result success() {
        return generateResult(SUCCESS_CODE, SUCCESS_MSG);
    }

    /**
     * 返回正确Result,并指定信息
     * @param desc 状态码描述
     * @return
     */
    public static Result success(String desc) {
        return generateResult(SUCCESS_CODE, desc);
    }

    /**
     * 返回正确Result,并指定信息
     * @param code 状态码
     * @param desc 状态码描述
     * @return
     */
    public static Result success(String code, String desc) {
        return generateResult(code, desc);
    }

}
