package com.api.user;

import java.util.List;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWarDeployment;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UserApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}
	
	/*
	//this allows cors request to pass through need to make sure  to remove during final build.
	@Bean
	@ConditionalOnWarDeployment
	CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}*/
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods(HttpMethod.GET.name(),
										HttpMethod.POST.name(),
										HttpMethod.OPTIONS.name(),
										HttpMethod.PUT.name())
						.allowedHeaders("*")
						.allowCredentials(true)
						.allowedOrigins("http://localhost:3000");
				//registry.addMapping("/v1/csrf").allowedOrigins("*");
				//registry.addMapping("/register").allowedOrigins("*");
			}
		};
	}
	
}
