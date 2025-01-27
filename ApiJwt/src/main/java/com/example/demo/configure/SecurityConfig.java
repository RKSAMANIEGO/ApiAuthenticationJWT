package com.example.demo.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Jwt.AuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsService detailsService;
	private final AuthenticationFilter authenticationFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(c->c.disable())
				.sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				
				.authorizeHttpRequests(req -> {
					req.requestMatchers("/auth/log-in", "/api/usuario").permitAll();
					req.requestMatchers("/api/usuario/eliminar/{id}").hasAnyRole("ADMINISTRADOR");
					req.anyRequest().authenticated();
				})
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dao= new DaoAuthenticationProvider();
		dao.setUserDetailsService(detailsService);
		dao.setPasswordEncoder(encode());
		return dao;
	}
	
	@Bean
	PasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	
}
