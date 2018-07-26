package cn.org.miny.common;

import cn.org.miny.dto.MiniProgramSession;
import cn.org.miny.util.HttpUtil;

/**
 * 小程序http接口工具类
 * Created by limingyang on 2018/7/19.
 */
public class MiniProgramAPIUtil {


    /**
     * 登录凭证校验
     * @param jscode 临时登录凭证code
     * @return
     * @throws Exception
     */
    public static MiniProgramSession jscode2session(String jscode) throws Exception {
        String url = String.format(Configure.JSCODE_2_SESSION_URL, Configure.APPID, Configure.SECRET, jscode);
        return HttpUtil.get(url, MiniProgramSession.class);
    }

}
