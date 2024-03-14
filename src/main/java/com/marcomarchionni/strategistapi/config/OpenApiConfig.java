package com.marcomarchionni.strategistapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Strategist API")
                        .description("""
                                Portfolio Strategist is a web application designed to empower stock and option
                                traders by providing tools to organize, view, and query their trading data from an
                                Interactive Brokers account. By appending custom tags to their trading data, users
                                can query data and analyze performance at a refined level, enabling them to make
                                more informed decisions.<br>
                                Strategist API is a RESTful API that provides access to the Strategist database. The API
                                is designed to be consumed by the Portfolio Strategist web application.
                                """)
                        .version("1.0.0"));
    }

    @Bean
    public OpenApiCustomizer customiseSchemas() {
        return openApi -> {
            List<String> schemasToRemove = List.of("TradeFind");
            schemasToRemove.forEach(openApi.getComponents().getSchemas()::remove);
        };
    }
}
