package com.itmo.mpa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class SpringFoxConfig : WebMvcConfigurer {

    @Bean
    fun apiDocket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.itmo.mpa.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
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