package com.example.coronavirustracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerConfiguration(){
        //Return a prepared DoCKET Configuration instance
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/corona/**"))
                .apis(RequestHandlerSelectors.basePackage("com.example.coronavirustracker.controller"))
                /*.paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.any())*/
                .build()
                .apiInfo(setApiInfo());
    }

    private ApiInfo setApiInfo() {
        return new ApiInfo(
                "Corona Virus Tracker Api",
                "Sample Api for learning Spring Boot",
                "1.0",
                "Free to use",
                new Contact("Prateek Mazumdar", "linkedin.com/in/prateek-mazumdar-177858184", "prateekm.ind@gmail.com"),
                "Api License",
                "github.com/prateekm-ind?tab=repositories",
                Collections.emptyList()
        );
    }
}
