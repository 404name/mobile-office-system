package com.curity.office.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author zhangyu
 * @Date 2020/6/15
 * @Description
 **/

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@ConditionalOnProperty(value = {"knife4j.enable"}, matchIfMissing = true)
public class Swagger2Config {

    /**
     * 前台API分组
     *
     * @return
     */
    @Bean(value = "indexApi")
    public Docket indexApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统API分组")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("xyz.erupt"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 后台API分组
     *
     * @return
     */
    @Bean(value = "adminApi")
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台API分组")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.curity.office.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger-bootstrap-ui RESTful APIs")
                .description("swagger-bootstrap-ui")
                .termsOfServiceUrl("http://localhost:8080/")
                .contact(new Contact("404name","404name.top","1308964967@qq.com"))
                .version("1.0")
                .build();
    }

}
