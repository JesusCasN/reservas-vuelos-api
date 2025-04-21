package com.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Reservas de Vuelos API",
                description = "API para gestionar reservas de vuelos con seguridad JWT",
                version = "1.0",
                contact = @Contact(
                        name = "José de Jesús Castillo Nolasco",
                        email = "jesuscastillonolasco@gmail.com",
                        url = "https://jesus-portafolio.netlify.app/"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

}
