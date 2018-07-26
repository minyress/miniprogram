package cn.org.miny.dto;

import com.alibaba.fastjson.JSON;

/**
 *
 * Created by limingyang on 2018/7/18.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        String json = "{\"errcode\":40029,\"errmsg\":\"invalid code, hints: [ req_id: .1O8ua03162275 ]\"}";
        MiniProgramSession miniProgramSession = JSON.parseObject(json, MiniProgramSession.class);
        System.err.println(miniProgramSession.getErrmsg());
    }


}
