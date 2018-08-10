package cn.org.miny.service.impl;

//import cn.org.miny.dao.IUserDao;

import cn.org.miny.common.Configure;
import cn.org.miny.common.MiniProgramAPIUtil;
import cn.org.miny.dto.MiniProgramSession;
import cn.org.miny.dto.MiniProgramUserDTO;
import cn.org.miny.mapper.MiniProgramUserMapper;
import cn.org.miny.model.MiniProgramUser;
import cn.org.miny.service.MiniService;
import cn.org.miny.util.RedisUtil;
import cn.org.miny.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by limingyang on 2018/7/17.
 */
@Service("miniService")
public class MiniServiceImpl implements MiniService {

    /**
     *
     */
    protected static final Logger logger = LoggerFactory.getLogger(MiniServiceImpl.class);

    @Autowired
    private MiniProgramUserMapper miniProgramUserMapper;

    @Override
    public String miniProgramLogin(String jscode) throws Exception {
        MiniProgramSession miniProgramSession = MiniProgramAPIUtil.jscode2session(jscode);
        if (!ObjectUtils.isEmpty(miniProgramSession.getErrcode())) {
            throw new Exception("小程序登录失败," + miniProgramSession.getErrmsg());
        } else if (ObjectUtils.isEmpty(miniProgramSession) || StringUtils.isEmpty(miniProgramSession.getOpenid()) || StringUtils.isEmpty(miniProgramSession.getSessionKey())) {
            throw new Exception("小程序登录失败,未知错误!");
        }
        MiniProgramUser miniProgramUser = this.miniProgramUserMapper.findByOpenId(miniProgramSession.getOpenid());
        if (ObjectUtils.isEmpty(miniProgramUser)) {
            Date now = new Date();
            miniProgramUser = new MiniProgramUser();
            miniProgramUser.setId(UUID.randomUUID().toString());
            miniProgramUser.setNickName("未获取用户信息");
            miniProgramUser.setOpenid(miniProgramSession.getOpenid());
            miniProgramUser.setJoinTime(now);
            this.miniProgramUserMapper.initUser(miniProgramUser);
        }
        RedisUtil.set(miniProgramSession.getSessionKey(), miniProgramUser.getOpenid(), Configure.TOKEN_EXP, TimeUnit.MILLISECONDS);
        return TokenUtil.createToken(miniProgramSession.getSessionKey());
    }

    @Override
    public MiniProgramUser findMiniUserByOpenid(String openid) {
        MiniProgramUser user = null;
        String cacheKey = "user_" + openid;
        if (RedisUtil.hasKey(cacheKey)) {
            String json = RedisUtil.get(cacheKey);
            user = JSON.parseObject(json, MiniProgramUser.class);
        } else {
            user = this.miniProgramUserMapper.findByOpenId(openid);
            RedisUtil.set(cacheKey, JSON.toJSONString(user));
        }
        return user;
    }

    @Override
    public void miniProgramUser(MiniProgramUserDTO user, MiniProgramUser miniProgramUser) throws Exception {
        if (ObjectUtils.isEmpty(user)) {
            throw new Exception("修改的用户信息为空");
        } else if (ObjectUtils.isEmpty(miniProgramUser)) {
            throw new Exception("登录用户为空");
        }
        miniProgramUser.setNickName(user.getNickName());
        miniProgramUser.setGender(user.getGender());
        miniProgramUser.setLanguage(user.getLanguage());
        miniProgramUser.setCity(user.getCity());
        miniProgramUser.setProvince(user.getProvince());
        miniProgramUser.setCountry(user.getCountry());
        miniProgramUser.setAvatarUrl(user.getAvatarUrl());
        this.miniProgramUserMapper.updateUser(miniProgramUser);
    }


}
