package com.example.demo.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
        info = @Info(

                title = "BASKETBALL API DOCUMENT",
                description = "API DOCUMENT",
                version = "v0.1",
                contact = @Contact(
                        name = "BASKETBALL",
                        email = "admin@hoops.com"
                )

        ),
        tags = {
                @Tag(name = "1. USER", description = "회원 기능")
        }


)

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes("bearerAuth", securityScheme)
                ).security(Arrays.asList(securityRequirement));
    }


}
