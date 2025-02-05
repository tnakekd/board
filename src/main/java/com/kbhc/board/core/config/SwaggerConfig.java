package com.kbhc.board.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .group("public")
                        .pathsToMatch("/api/**")
                        .build();
        }

        @Bean
        public OpenAPI openApi() {
                return new OpenAPI()
                        .info(new io.swagger.v3.oas.models.info.Info()
                                .title("KBHC Board API")
                                .description("KB 헬스케어 게시판 과제 API 문서 입니다")
                                .contact(new io.swagger.v3.oas.models.info.Contact()
                                        .name("Developer")
                                        .email("tnakekd@naver.com")))
                        .addSecurityItem(new SecurityRequirement().addList("apiKey"))
                        .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("apiKey", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                                .description("Base64 encoded username:password")
                        ))
                        ;
        }

}
