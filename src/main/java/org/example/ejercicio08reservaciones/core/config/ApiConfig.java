package org.example.ejercicio08reservaciones.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
        @Bean
        public OpenAPI myOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("API para la gestión de reservaciones")
                                                .version("1.0")
                                                .description("API REST para la gestión de Cuartos y Reservaciones")
                                                .contact(new Contact()
                                                                .name("Cristhian")
                                                                .email("cristhanatz@gmail.com")
                                                                .url("https://cristhanatzgmail.com"))
                                                .license(new License()
                                                                .name("Apache 2.0")
                                                                .url("El url de la licencia")));
        }

}
