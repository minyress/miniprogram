package cn.org.miny.dto;

import lombok.Data;

/**
 *
 * Created by limingyang on 2018/7/19.
 */
@Data
public class MiniProgramSession extends MiniProgramResult {

    /**
     * 用户唯一标识
     */
    private String openid;

    /**
     * 会话密钥
     */
    private String sessionKey;

    /**
     * 用户在开放平台的唯一标识符
     */
    private String unionid;

}
