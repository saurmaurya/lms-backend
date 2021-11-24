package com.srsdev.tech.lms.configs

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(
    @Value("\${lms.app.jwtSecret}")
    private val jwtSecret: String,
) {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes(
                        jwtSecret,
                        SecurityScheme().type(SecurityScheme.Type.HTTP)
                    .scheme("Bearer").bearerFormat("JWT")
            ))
    }
}