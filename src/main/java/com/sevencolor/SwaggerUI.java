/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 生成Controller REST接口文档,访问地址：http://IP:port/{context-path}/swagger-ui.html
 * @author: yinhong
 * @date: 2016年11月24日 下午12:06:06
 * @version: V1.0
 */
package com.sevencolor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.sevencolor.comm.util.MessageUtil;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description: 生成Controller REST接口文档,访问地址：http://IP:port/{context-path}/swagger-ui.html
 * @date: 2016年11月24日 下午12:06:06
 */
@EnableSwagger2
public class SwaggerUI extends WebMvcConfigurationSupport {

  /**
   * @Description: 使用SwaggerUI创建应用自己的ApiInfo
   * @return: Docket
   */
  @SuppressWarnings("deprecation")
  @Bean
  public Docket createRestApi() {

    Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any())
        .build().pathMapping("/").directModelSubstitute(LocalDate.class, Date.class)
        .useDefaultResponseMessages(false);

    ApiInfo apiInfo = new ApiInfoBuilder()
        .title(MessageUtil.getMessage("message.swaggerui.title", null, "", null))
        .description(MessageUtil.getMessage("message.swaggerui.description", null, "", null))
        .license("").licenseUrl("")
        .contact(MessageUtil.getMessage("message.swaggerui.creator", null, "", null))
        .version(MessageUtil.getMessage("message.comm.version", null, "", null)).build();

    if (!Objects.isNull(apiInfo)) {
      docket.apiInfo(apiInfo);
    }

    return docket;
  }

}
