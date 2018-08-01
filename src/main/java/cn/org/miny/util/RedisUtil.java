package cn.org.miny.util;

import com.alibaba.fastjson.JSON;
import lombok.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.ContextLoader;

import java.util.concurrent.TimeUnit;

/**
 * Redis操作类
 * Created by limingyang on 2018/7/19.
 */
public class RedisUtil {

    /**
     * 方式一
     */
//    private static RedisTemplate<String, String> template = ContextLoader.getCurrentWebApplicationContext().getBean("stringRedisTemplate", StringRedisTemplate.class);

    /**
     * 方式二
     */
    private static StringRedisTemplate stringRedisTemplate = ContextLoader.getCurrentWebApplicationContext().getBean("stringRedisTemplate", StringRedisTemplate.class);

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
        stringRedisTemplate.opsForValue().set(key, (String) value);
    }

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    public static String get(@NonNull String key) {
        return stringRedisTemplate.opsForValue().get(key);
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
     * 删除key
     * @param key
     */
    public static void delete(@NonNull String key) {
        if (stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.delete(key);
        }
    }

    /**
     * key是否存在
     * @param key
     * @return
     */
    public static boolean hasKey(@NonNull String key) {
        return stringRedisTemplate.hasKey(key);
    }


}
