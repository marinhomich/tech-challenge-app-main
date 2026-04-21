package com.oficina.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tech Challenge - Oficina Management API")
                        .version("1.0")
                        .description("Sistema Integrado de Gestão de Oficina Mecânica\n\n" +
                                "Este sistema permite:\n" +
                                "- Gestão de ordens de serviço com workflow completo\n" +
                                "- Gestão de clientes com validação de CPF/CNPJ\n" +
                                "- Gestão de veículos com validação de placa\n" +
                                "- Gestão de peças e estoque\n" +
                                "- Gestão de serviços\n" +
                                "- Autenticação JWT para APIs administrativas"))
                .externalDocs(new ExternalDocumentation()
                        .description("README")
                        .url("/README.md"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
