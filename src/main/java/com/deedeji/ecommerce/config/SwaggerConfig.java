package com.deedeji.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
//@EnableSwagger2WebMvc
@EnableSwagger2
//@EnableWebMvc
@Component
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
	public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.deedeji.ecommerce"))
            .paths(PathSelectors.ant("/api/*"))
            .build()
//            .directModelSubstitute(LocalDate.class, java.sql.Date.class)
//            .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
            .apiInfo(apiInfo());
        }
        	private ApiInfo apiInfo() {
                Contact contact = new Contact("adeola-ecommerce", "", "deolaoladeji@gmail.com");
        		return new ApiInfoBuilder()
        			.title("Ecommerce API")
        			.description("Documentation of Ecommerce api")
        			.version("1.0.0")
        			.contact(contact)
        			.build();
        	}

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addRedirectViewController("/configuration/ui", "/swagger-resources/configuration/ui");
		}

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
			registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		}
}
