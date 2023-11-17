package tn.tym.esprit.configuration;

import io.swagger.v3.oas.models.Components;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(infoAPI());
    }
    public Info infoAPI() {
        return new Info().title("Train Your Mind")
                .description("TYM")
                .contact(contactAPI());
    }
    public Contact contactAPI() {
        Contact contact = new Contact().name("TRAIN YOUR MIND")
                .email("yosra.dab@esprit.tn")
                .url("https://www.linkedin.com/in/**********/");
        return contact;
    }

    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("ValidationErrors", new ObjectSchema()
                                .addProperties("fieldName", new StringSchema().example("firstName"))
                                .addProperties("errorMessage", new StringSchema().example("Le prénom ne peut pas être vide"))))
                .info(new Info().title("Mon API").version("1.0.0"));
    }
    }



