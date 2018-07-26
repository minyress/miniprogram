package cn.org.miny.controller;

import cn.org.miny.common.BaseController;
import cn.org.miny.common.TokenResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 小程序登录控制器
 * Created by limingyang on 2018/7/17.
 */
@Api(value = "/login", description = "登录控制器")
@Controller
@RequestMapping("/mini/login")
public class LoginController extends BaseController {

    /**
     * 登录接口
     * @param jscode wx.login接口得到的code
     * @return
     * @throws Exception
     */
    @ApiOperation(
            value = "小程序登录接口"
            , response = TokenResult.class
            , notes = "必要的参数为wx.login接口返回的code, 返回token参数. 需要将该token在之后请求数据接口时加到headers中"
    )
    @ResponseBody
    @RequestMapping(value = {"/jscode2session"}, method = {RequestMethod.GET})
    public TokenResult jscode2session(@RequestParam(required = true) @ApiParam(value = "wx.login接口返回的code", required = true) String jscode) throws Exception {
        String token = this.miniService.miniProgramLogin(jscode);
        return TokenResult.success(token);
    }


}
