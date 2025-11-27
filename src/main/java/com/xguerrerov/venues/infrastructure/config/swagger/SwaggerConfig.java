package com.xguerrerov.venues.infrastructure.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        Contact contact = new Contact()
                .name("xguerrerov")
                .email("xguerrerov0903@gmail.com");

        Info info = new Info()
                .title("Event Catalog API")
                .version("1.0")
                .description("REST API for managing Events and Venues in-memory")
                .contact(contact);

        List<Tag> tags = List.of(
                new Tag().name("Events").description("Operations related to event management"),
                new Tag().name("Venues").description("Operations related to venue management")
        );
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
