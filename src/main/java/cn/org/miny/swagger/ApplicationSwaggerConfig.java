package cn.org.miny.swagger;

import cn.org.miny.common.Configure;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
//@org.springframework.context.annotation.ComponentScan(basePackageClasses = {MiniProgramBusinessController.class})
public class ApplicationSwaggerConfig {

    /**
     * 方式一：
     * 需要在类头部加注释：@org.springframework.context.annotation.ComponentScan(basePackageClasses = {XXXXController.class})
     * @return
     */
//    @Bean
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .useDefaultResponseMessages(false)
//                .securitySchemes(Lists.newArrayList(apiKey()))
//                .securityContexts(Lists.newArrayList(securityContext()))
//                .apiInfo(apiInfo());
//    }


    /**
     * 方式二
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.org.miny.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .securitySchemes(Lists.newArrayList(this.apiKey()))
                .securityContexts(Lists.newArrayList(this.securityContext()))
                .apiInfo(apiInfo())
                .enable(ObjectUtils.isEmpty(Configure.IS_ENABLE_SWAGGER_UI) ? false : Configure.IS_ENABLE_SWAGGER_UI)
                ;
    }


    /**
     * 验证token, 当Docket调用securitySchemes(),securityContexts()时 则启用@ApiOperation.authorizations
     * 参考文档: http://springfox.github.io/springfox/docs/current/#introduction
     * @return
     */
    private ApiKey apiKey() {
        return new ApiKey("token", "token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(this.defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("token", authorizationScopes));
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台接口文档与测试")
                .description("接口的测试文档与平台")
                .version("1.0.0")
                .termsOfServiceUrl("http://terms-of-services.url")
                .build();
    }


}
