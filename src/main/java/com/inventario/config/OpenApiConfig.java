package com.inventario.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API REST de Gestión de Inventario con JWT",
                version = "2.0.0",
                description = "API REST protegida con Spring Security y autenticación JSON Web Token (JWT). " +
                        "Las operaciones CRUD de productos requieren el token Bearer en el encabezado Authorization.",
                contact = @Contact(name = "UCEVA - Electiva Profesional I")
        ),
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Ingrese su token JWT generado en /api/auth/login con el prefijo Bearer"
)
public class OpenApiConfig {
}
