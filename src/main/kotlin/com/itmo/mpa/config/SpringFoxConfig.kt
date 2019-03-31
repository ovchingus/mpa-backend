package com.itmo.mpa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import java.util.Collections

@Configuration
@EnableSwagger2
class SpringFoxConfig {
    @Bean
    fun apiDocket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.itmo.mpa.controller"))
                .paths(PathSelectors.any())
                .build()
    }

    private fun getApiInfo(): ApiInfo {
        return ApiInfo(
                "ITMO-MPA",
                "",
                "V1",
                "",
                Contact("ITMO", "URL", "EMAIL"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        )
    }
}