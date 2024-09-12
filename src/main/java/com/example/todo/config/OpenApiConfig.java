package com.example.todo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task API")
                        .version("1.0")
                        .description("API для управления задачами")
                        .contact(new io.swagger.v3.oas.models.info.Contact().email("beka.aalyev@gmail.com")));
    }
}
