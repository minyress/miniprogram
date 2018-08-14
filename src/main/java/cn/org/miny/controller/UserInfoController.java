package cn.org.miny.controller;

import cn.org.miny.common.BaseController;
import cn.org.miny.common.Result;
import cn.org.miny.dto.MiniProgramUserDTO;
import cn.org.miny.model.MiniProgramUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户控制器
 * Created by limingyang on 2018/7/17.
 */
@Api(value = "/userinfo", description = "用户控制器")
@Controller
@RequestMapping("/mini")
public class UserInfoController extends BaseController {

    @ApiOperation(
            value = "更新用户信息"
            , response = Result.class
            , notes = "更新用户信息"
            , authorizations = {@Authorization(value = "token")}
    )
    @ResponseBody
    @RequestMapping(value = {"/userinfo"}, method = {RequestMethod.PUT})
    public Result updateUserinfo(@RequestBody(required = true) MiniProgramUserDTO user) throws Exception {
        MiniProgramUser miniProgramUser = super.getMiniProgramUser();
        this.miniService.updateUserinfo(user, miniProgramUser);
        return super.success();
    }

}
