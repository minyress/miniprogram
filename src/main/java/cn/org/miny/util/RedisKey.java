package cn.org.miny.util;

/**
 * Redis 所有的键
 * key设计规范 https://love2.io/@hakukata/doc/All-About-Redis/CodeDesignRule/keydesign.md
 *
 * Created by limingyang on 2018-8-14 10:46:52
 */
public class RedisKey {

    /**
     * 用户sessionKey
     */
    public static final String USER_SESSION_KEY = "user:sessionKey:";

    /**
     * 用户openid
     */
    public static final String USER_OPENID = "user:openid:";

}
