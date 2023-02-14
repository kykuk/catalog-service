package com.polarbookshop.catalogservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info(">>> Applied SecurityFilterChain Bean");
		
		return http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.GET, "/", "/books/**").permitAll()
				.anyRequest().hasRole("employee")
			)
			.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
			.sessionManagement(sessionManagement -> 
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(AbstractHttpConfigurer::disable)
			.build();
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		log.info(">>> Applied JwtAuthenticationConverter Bean");
		var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
		
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}
}
