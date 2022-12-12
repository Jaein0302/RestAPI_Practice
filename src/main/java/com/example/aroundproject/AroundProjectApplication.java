package com.example.aroundproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableWebMvc //왜쓰는지 모르겠음
public class AroundProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AroundProjectApplication.class, args);
    }

}
