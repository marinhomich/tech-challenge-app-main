package com.oficina.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {
    @Test
    void testCustomOpenAPI() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI openAPI = config.customOpenAPI();
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Tech Challenge - Oficina Management API", openAPI.getInfo().getTitle());
        assertNotNull(openAPI.getComponents());
        assertTrue(openAPI.getComponents().getSecuritySchemes().containsKey("Bearer Authentication"));
    }
}
