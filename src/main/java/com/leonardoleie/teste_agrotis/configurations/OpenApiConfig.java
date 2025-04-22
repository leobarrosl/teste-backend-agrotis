package com.leonardoleie.teste_agrotis.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Agrotis")
                        .version("1.0")
                        .description("API de resolução do teste da empresa Agrotis")
                        .contact(new Contact()
                                .name("Leonardo Leite")
                                .email("leonardoleite99@gmail.com")));
    }
}