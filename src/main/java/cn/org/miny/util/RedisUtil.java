package cn.org.miny.util;

import com.alibaba.fastjson.JSON;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by limingyang on 2018/7/19.
 */
public class RedisUtil {

    private static final Map<String, Object> REDIS = new HashMap<>();

    /**
     * 设置value
     *
     * @param key
     * @param value
     */
    public static void set(@NonNull String key, @NonNull Object value) {
        set(key, value, 100000, TimeUnit.HOURS);
    }

    /**
     * 设置value
     *
     * @param key
     * @param value
     * @param timeout  有效时间
     * @param timeUnit 时间单位
     */
    public static void set(@NonNull String key, @NonNull Object value, @NonNull long timeout, @NonNull TimeUnit timeUnit) {
        REDIS.put(key, value);
    }

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    public static String get(@NonNull String key) {
        return String.valueOf(REDIS.get(key));
    }

    /**
     * 获取value对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T get(@NonNull String key, Class<T> clazz) {
        return JSON.parseObject(get(key), clazz);
    }

    /**
     * 删除
     *
     * @param key
     */
    public static void delete(@NonNull String key) {
        REDIS.remove(key);
    }
}
