package cn.org.miny.controller;

import cn.org.miny.common.BaseController;
import cn.org.miny.common.Result;
import cn.org.miny.model.MiniProgramUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 业务控制器
 * Created by limingyang on 2018/7/17.
 */
@Api(value = "/business", description = "业务控制器")
@Controller
@RequestMapping("/mini")
public class BusinessController extends BaseController {

    @ApiOperation(
            value = "测试接口"
            , response = Result.class
            , notes = "请求时需要将token加到headers中"
            , authorizations = {@Authorization(value = "token")}
    )
    @ResponseBody
    @RequestMapping(value = {"/test"}, method = {RequestMethod.GET})
    public Result test() throws Exception {
        MiniProgramUser miniProgramUser = super.getMiniProgramUser();
        logger.info(miniProgramUser.toString());
        return super.success();
    }


}
