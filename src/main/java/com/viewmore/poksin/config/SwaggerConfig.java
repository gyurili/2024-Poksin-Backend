package com.viewmore.poksin.config;


import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("2024 중앙 해커톤 백엔드 poksin API")
                        .description("2024 중앙 해커톤 백엔드 poksin API 명세서")
                        .version("1.0.0"));
    }
}
