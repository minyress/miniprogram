package cn.org.miny.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Controller 返回类
 * Created by limingyang on 2018/7/18.
 */
@Data
public class TokenResult extends Result {

    /**
     * token
     */
    @ApiModelProperty(value = "token, 在之后请求数据接口时加到head中",position = 2)
    private String token;

    TokenResult(String token) {
        super();
        this.token = token;
    }

    public static TokenResult success(String token) {
        return new TokenResult(token);
    }
}
