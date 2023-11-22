package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class})
public class InvoiceManagerApplication {
	private static final int STRENGHT = 12;

	public static void main(String[] args) {
		SpringApplication.run(InvoiceManagerApplication.class, args);
	}
@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(STRENGHT);
}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:3000", "https://invoice-app-frontend-95342-29caa4a707ee.herokuapp.com", "www.invoice-mng.com", "invoice-mng.com", "https://www.invoice-mng.com"));
		//corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Jwt-Token", "Authorization", "Origin", "Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "File-Name"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@Bean
	public ApplicationRunner printEnvProperties(Environment environment) {
		return args -> {
			System.out.println("=============== Properties Start ===============");
			((ConfigurableEnvironment) environment).getPropertySources().stream()
					.filter(ps -> ps instanceof EnumerablePropertySource)
					.map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
					.flatMap(Arrays::stream)
					.distinct()
					.forEach(prop -> System.out.println(prop + ": " + environment.getProperty(prop)));
			System.out.println("=============== Properties End   ===============");
		};
	}

}
