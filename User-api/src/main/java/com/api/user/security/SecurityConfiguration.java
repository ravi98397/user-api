package com.api.user.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.api.user.model.Authority;
import com.api.user.model.UserLogin;
import com.api.user.service.impl.UserLoginService;

@Configuration
public class SecurityConfiguration {
	
	@Autowired
	private UserLoginService loginService;
	
	@Bean
	@Primary
	UserLoginService userDetailsService() {
		
		//UserLoginService loginService = new UserLoginService();
		
		Authority auth1 = new Authority("ADMIN", "has access to everything.");
		Authority auth2 = new Authority("USER", "has access to everything.");
		
		ArrayList<Authority> authorities = new ArrayList<>();
		authorities.add(auth1);
		
		UserLogin admin = new UserLogin("admin", passowrdEncoder().encode("password"), Set.of(auth1));
		loginService.adduser(admin);
		
		return new UserLoginService(); 
	}
	
	@Bean
	DaoAuthenticationProvider authenticationManager() {
		DaoAuthenticationProvider a = new DaoAuthenticationProvider();
		a.setPasswordEncoder(passowrdEncoder());
		a.setUserDetailsService(userDetailsService());
		return a;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.applyPermitDefaultValues();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOrigin("https://localhost:3000");
		configuration.setAllowedHeaders(List.of(
										HttpHeaders.AUTHORIZATION,
										"X-XSRF-TOKEN",
										HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
		
		configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(),
													  HttpMethod.POST.name(),
													  HttpMethod.OPTIONS.name(),
													  HttpMethod.PUT.name(),
													  HttpMethod.DELETE.name()));
		configuration.addExposedHeader("Set-Cookie");
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and()
//			.cors().disable()
			.authorizeHttpRequests((authz) -> authz
					.antMatchers(HttpMethod.GET, "/v1/csrf").permitAll()
//					.antMatchers(HttpMethod.GET,  "/hello").permitAll()
					.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.anyRequest().authenticated()
					)
			.httpBasic(Customizer.withDefaults());
		http.formLogin();
		return http.build();
	}
	
	/*
	@Bean
	UserDetailsManager users(DataSource dataSource) {
		
		Authority auth1 = new Authority("ADMIN", "has access to everything.");
		Authority auth2 = new Authority("USER", "has access to everything.");
		
		ArrayList<Authority> authorities = new ArrayList<>();
		authorities.add(auth1);
		
		UserDetails a = User.builder().username("ravi").password("hello").build();
		
		 UserDetails user = User.builder()
			.username("user")
			.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
			.authorities(Set.of(auth1,auth2))
			.build();
		UserDetails admin = User.builder()
			.username("admin")
			.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
			.authorities(Set.of(auth1))
			.build();
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.createUser(user);
		users.createUser(admin);
		return users;
	}*/
	
	//WebSecurityConfigurerAdapter -> is deprecated we need to use bean of bean for HTTP security and Web security
	//to modify the HTTP or web configuration.
	
	@Bean
	PasswordEncoder passowrdEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
