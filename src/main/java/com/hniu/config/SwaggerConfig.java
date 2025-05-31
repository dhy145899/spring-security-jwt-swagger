package com.hniu.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "我的api接口文档",
                version = "1.0",
                description = "springboot项目的api文档",
                //联系名，邮箱，url
                contact = @Contact(name = "dhy team", email = "2775268015@qq.com", url = "dhy@dhy.com"),
                //许可证信息，包括名称和 URL
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"),
                //服务条款的 URL
                termsOfService = "https://example.com/terms"
        )
)
@SecurityScheme(
        //认证方案的名称 使用@SecurityRequirement引用
        name = "bearerAuth",
        //认证类型，此处是HTTP
        type = SecuritySchemeType.HTTP,
        //认证方案，表示使用Bearer Token
        scheme = "bearer",
        //令牌格式，JWT
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("Spring Security JWT Mybatis-plus API")
                        .description("Spring Security with JWT Mybatis-plus app")
                        .version("1.0")
                );
    }
}
