package cn.org.miny.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 配置类
 * 读取类路径下的config.properties的文件
 * config.properties配置文件的key与Configure类的字段命名需要完全相等
 * config.properties:
 *
 * Configure:
 * 静态字段未赋值时,将赋予config.properties的值. 已赋值则忽略config.properties的值
 * Created by limingyang on 2018/7/19.
 */
public class Configure {

    Configure() {

    }

    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 小程序ID
     */
    public static String APPID;

    /**
     * 小程序密钥
     */
    public static String SECRET;

    /**
     * 小程序登录凭证校验HTTPS接口
     */
    public static String JSCODE_2_SESSION_URL;

    /**
     * 获取 access_token HTTPS接口
     */
    public static String GET_TOKEN_URL;

    /**
     * Jwt - BASE64编码的特定于算法的签名密钥，用于对数字签名
     */
    public static String TOKEN_SECRET;

    /**
     * Jwt - token有效时间,单位: 毫秒
     */
    public static Long TOKEN_EXP;

    /**
     * 是否启用Swagger UI. url: http://localhost:8080/swagger/index.html
     */
    public static Boolean IS_ENABLE_SWAGGER_UI;

    static void init() {
        Properties props = new Properties();
        InputStream is = null;
        try {
            Class<Configure> clz = Configure.class;
            is = clz.getResourceAsStream("/config.properties");
            if (ObjectUtils.isEmpty(is)) {
                logger.info("警告：缺少config.properties文件！！！");
                return;
            }
            props.load(is);
            //读取必要字段
            Object appid = props.remove("APPID");
            if (StringUtils.isEmpty(Configure.APPID)) {
                if (StringUtils.isEmpty(appid)) {
                    logger.info("警告：config.properties文件缺少[APPID]必要字段");
                } else {
                    Configure.APPID = String.valueOf(appid);
                }
            }

            Object secret = props.remove("SECRET");
            if (StringUtils.isEmpty(Configure.SECRET)) {
                if (StringUtils.isEmpty(secret)) {
                    logger.info("警告：config.properties文件缺少[SECRET]必要字段");
                } else {
                    Configure.SECRET = String.valueOf(secret);
                }
            }

            Object jscode2SessionUrl = props.remove("JSCODE_2_SESSION_URL");
            if (StringUtils.isEmpty(Configure.JSCODE_2_SESSION_URL)) {
                if (StringUtils.isEmpty(jscode2SessionUrl)) {
                    logger.info("警告：config.properties文件缺少[JSCODE_2_SESSION_URL]必要字段");
                } else {
                    Configure.JSCODE_2_SESSION_URL = String.valueOf(jscode2SessionUrl);
                }
            }

            Object getTokenUrl = props.remove("GET_TOKEN_URL");
            if (StringUtils.isEmpty(Configure.GET_TOKEN_URL)) {
                if (StringUtils.isEmpty(getTokenUrl)) {
                    logger.info("警告：config.properties文件缺少[GET_TOKEN_URL]必要字段");
                } else {
                    Configure.GET_TOKEN_URL = String.valueOf(getTokenUrl);
                }
            }

            Object tokenSecret = props.remove("TOKEN_SECRET");
            if (StringUtils.isEmpty(Configure.TOKEN_SECRET)) {
                if (StringUtils.isEmpty(tokenSecret)) {
                    logger.info("警告：config.properties文件缺少[TOKEN_SECRET]必要字段");
                } else {
                    Configure.TOKEN_SECRET = String.valueOf(tokenSecret);
                }
            }

            Object tokenExp = props.remove("TOKEN_EXP");
            if (ObjectUtils.isEmpty(Configure.TOKEN_EXP)) {
                if (ObjectUtils.isEmpty(tokenExp)) {
                    logger.info("警告：config.properties文件缺少[TOKEN_EXP]必要字段");
                } else {
                    Configure.TOKEN_EXP = NumberUtils.parseNumber(String.valueOf(tokenExp), Long.class);
                }
            }

            //读取非必要字段
            Object isEnableSwaggerUi = props.remove("IS_ENABLE_SWAGGER_UI");
            Configure.IS_ENABLE_SWAGGER_UI = ObjectUtils.isEmpty(Configure.IS_ENABLE_SWAGGER_UI)
                    ? (ObjectUtils.isEmpty(isEnableSwaggerUi) ? false : Boolean.valueOf(String.valueOf(isEnableSwaggerUi)))
                    : Configure.IS_ENABLE_SWAGGER_UI
            ;

            //读取自定义字段
            if (props.isEmpty()) {
                return;
            }
            Enumeration<?> enumeration = props.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement().toString();
                Field field = null;
                try {
                    field = Configure.class.getField(key);
                } catch (NoSuchFieldException e) {
                    logger.info("警告: Configure类中没有[" + key + "]字段!");
                    continue;
                }
                Object configValue = props.get(field.getName());
                if (StringUtils.isEmpty(String.valueOf(configValue))) {
                    continue;
                }
                Object value = convertValue(field.getType(), configValue);
                field.set(null, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据类型转换值
     * @param fieldType   类型
     * @param configValue 被转换的值
     * @return
     */
    private static Object convertValue(Class<?> fieldType, Object configValue) {
        Object value = null;
        if (fieldType.equals(Boolean.class)) {
            value = Boolean.valueOf(String.valueOf(configValue));
        } else if (fieldType.equals(Byte.class)) {
            value = NumberUtils.parseNumber(String.valueOf(configValue), Byte.class);
        } else if (fieldType.equals(Character.class)) {
            value = String.valueOf(configValue).charAt(0);
        } else if (fieldType.equals(Short.class)) {
            value = NumberUtils.parseNumber(String.valueOf(configValue), Short.class);
        } else if (fieldType.equals(Integer.class)) {
            value = NumberUtils.parseNumber(String.valueOf(configValue), Integer.class);
        } else if (fieldType.equals(Long.class)) {
            value = NumberUtils.parseNumber(String.valueOf(configValue), Long.class);
        } else if (fieldType.equals(Float.class)) {
            value = NumberUtils.parseNumber(String.valueOf(configValue), Float.class);
        } else if (fieldType.equals(Double.class)) {
            value = NumberUtils.parseNumber(String.valueOf(configValue), Double.class);
        } else if (fieldType.equals(String.class)) {
            value = String.valueOf(configValue);
        }
        return value;
    }

}
