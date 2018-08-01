package cn.org.miny.dto;

import cn.org.miny.model.MiniProgramUser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by limingyang on 2018/7/18.
 */
public class Main {
    public static void main(String[] args) throws Exception {


        redisTester();
    }

    public static void redisTester() {
//        Jedis jedis = new Jedis("localhost", 6379, 100000);
//        int i = 0;
//        try {
//            long start = System.currentTimeMillis();// 开始毫秒数
//            while (true) {
//                long end = System.currentTimeMillis();
//                if (end - start >= 1000) {// 当大于等于1000毫秒（相当于1秒）时，结束操作
//                    break;
//                }
//                i++;
//                jedis.set("test" + i, i + "");
//            }
//        } finally {// 关闭连接
//            jedis.close();
//        }
//        // 打印1秒内对Redis的操作次数
//        System.out.println("redis每秒操作：" + i + "次");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);
        MiniProgramUser user = new MiniProgramUser();
        user.setNickName("我没有三颗心脏");
        user.setGender(1);
        redisTemplate.opsForValue().set("user", user);
        MiniProgramUser cackeUser = (MiniProgramUser) redisTemplate.opsForValue().get("user");
        System.err.println(cackeUser);


    }


    public static void getRedisPoolBean() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大空闲数
        poolConfig.setMaxIdle(50);
        // 最大连接数
        poolConfig.setMaxTotal(100);
        // 最大等待毫秒数
        poolConfig.setMaxWaitMillis(20000);
        // 使用配置创建连接池
        JedisPool pool = new JedisPool(poolConfig, "localhost");
        // 从连接池中获取单个连接
        Jedis jedis = pool.getResource();
        // 如果需要密码
        //jedis.auth("password");
    }
}
