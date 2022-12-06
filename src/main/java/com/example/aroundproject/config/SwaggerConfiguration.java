package com.example.aroundproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * @package : studio.thinkground.aroundhub.config
 * @name : SwaggerConfiguration.java
 * @date : 2022-01-28 오후 4:34
 * @author : Flature
 * @version : 1.0.0
 **/

@Configuration
public class SwaggerConfiguration {

    // 접속 경로 : http://localhost:8080/swagger-ui/
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.aroundproject"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Test")
                .version("1.0")
                .description("설명")
                .build();
    }
}