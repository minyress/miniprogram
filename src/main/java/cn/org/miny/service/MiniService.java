package cn.org.miny.service;

import cn.org.miny.dto.MiniProgramUserDTO;
import cn.org.miny.model.MiniProgramUser;

/**
 *
 * Created by limingyang on 2018/7/17.
 */
public interface MiniService {

    /**
     * 登录小程序
     * @param jscode wx.login接口得到的code
     * @return
     * @throws Exception
     */
    String miniProgramLogin(String jscode) throws Exception;


    /**
     * 通过openid查询小程序用户
     * @param openid openid
     * @return
     */
    MiniProgramUser findMiniUserByOpenid(String openid);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @param miniProgramUser 当前登录用户
     */
    void miniProgramUser(MiniProgramUserDTO user, MiniProgramUser miniProgramUser) throws Exception;
}
