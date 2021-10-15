package com.dreamgamescase.TournamentApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dreamgamescase.TournamentApi"))
                .paths(PathSelectors.regex("/tournament.*"))
                .build()
                .apiInfo(info());
    }

    private ApiInfo info() {

        return new ApiInfo(
                "User API",
                "User API Description",
                "v1",
                "apiserviceurl",
                new Contact("Alper Aracı", "", "alper.araci@outlook.com"),
                "MIT License",
                "https://opensource.org/licenses/MIT"
        );
    }

}
