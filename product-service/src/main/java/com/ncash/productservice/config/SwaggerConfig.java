package com.ncash.productservice.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * The type Swagger config.
 */
@Configuration

public class SwaggerConfig   {

    /**
     * Spring open api open api.
     *
     * @return the open api
     */
    @Bean
    public OpenAPI springOpenAPI() {
      return new OpenAPI()
              .info(new Info().title("Product API")
              .description("Spring app for Product application")
              .version("v0.0.1")
              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
              .externalDocs(new ExternalDocumentation()
              .description("Product Wiki Documentation")
              .url("http://localhost:8080"));
  }


}
