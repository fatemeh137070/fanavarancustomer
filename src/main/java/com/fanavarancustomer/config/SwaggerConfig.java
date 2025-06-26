package com.fanavarancustomer.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🧑‍💻 سامانه مدیریت مشتریان اجاره سرور")
                        .version("v1.0.0")
                        .description("سامانه‌ای برای ثبت مشتریان، مدیریت سرویس‌ها، صدور فاکتور و پشتیبانی در حوزه اجاره سرور اختصاصی")
                        .contact(new Contact()
                                .name("تیم توسعه سامانه مشتریان")
                                .email("support@dedicatedserver.example.com")
                                .url("https://dedicatedserver.example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("مستندات کامل سامانه مدیریت مشتریان و سرویس‌های اختصاصی")
                        .url("https://dedicatedserver.example.com/docs"));
    }



}
