package cn.org.miny.mapper;

import cn.org.miny.model.MiniProgramUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *
 * Created by limingyang on 2018/7/18.
 */
public interface MiniProgramUserMapper {

    /**
     *
     * @param openId
     * @return
     */
    @Select("SELECT * FROM `all_miniprogram_user` WHERE `openid` = #{openId}")
    MiniProgramUser findByOpenId(@Param("openId") String openId);

    /**
     *
     * @param user
     */
    void initUser(MiniProgramUser user);

    /**
     *
     * @param user
     */
    void updateUser(MiniProgramUser user);

}
