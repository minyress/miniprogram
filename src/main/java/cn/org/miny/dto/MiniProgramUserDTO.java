package cn.org.miny.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 小程序用户PO
 * Created by limingyang on 2018/7/18.
 */
@Data
public class MiniProgramUserDTO implements Serializable {
    public MiniProgramUserDTO() {
    }

    /**
     *
     */
//    private String id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer gender;

    /**
     * 语言，简体中文为zh_CN
     */
    private String language;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 所在国家
     */
    private String country;

    /**
     * 头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表132*132正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    private String avatarUrl;

    /**
     * 唯一标识
     */
//    private String openid;

}
