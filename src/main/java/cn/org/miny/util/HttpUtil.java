package cn.org.miny.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class HttpUtil {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    /**
     * 发送get请求
     * @param url
     * @return
     * @throws Exception
     */
    private static String sendGet(String url) throws Exception {
        GetMethod getMethod = null;
        try {
            HttpClient httpClient = new HttpClient();
            int reqTimeout = 5000;
            int readTimeout = 30000;
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(reqTimeout);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(readTimeout);
            getMethod = new GetMethod(url);
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                return parseResponse(getMethod.getResponseBodyAsStream());
            } else {
                logger.info("调用httpclient方法get[" + url + "]失败," + statusCode);
            }
        } catch (Exception e) {
            logger.info("调用httpclient方法get[" + url + "]", e);
            throw e;
        } finally {
            if (getMethod != null) {
                getMethod.abort();
                getMethod.releaseConnection();
            }
        }
        return null;
    }

    /**
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String get(String url) throws Exception {
        return sendGet(url);
    }

    /**
     *
     * @param url
     * @param clz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T get(String url, Class<T> clz) throws Exception {
        return JSON.parseObject(sendGet(url), clz);
    }



    /**
     * 解析返回内容
     * @param is
     * @return
     * @throws Exception
     */
    private static String parseResponse(InputStream is) throws Exception {
        StringBuffer result = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }


}
