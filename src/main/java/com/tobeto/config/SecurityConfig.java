package com.tobeto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tobeto.filter.JwtAuthorizationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthorizationFilter jwtAuthorizationFilter;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/api/v1/login").permitAll()
				.requestMatchers("/api/v1/user/").hasRole("admin")
				.requestMatchers("/api/v1/user/get").permitAll()
				.requestMatchers("/api/v1/shelf").hasAnyRole("admin","depo_sorumlusu")
				.requestMatchers("/api/v1/shelf/get").permitAll()
				.requestMatchers("/api/v1/item").hasAnyRole("admin","depo_sorumlusu")
				.requestMatchers("/api/v1/item/opt").hasAnyRole("admin","depo_sorumlusu")
				.requestMatchers("/api/v1/item/get").permitAll()
				.requestMatchers("/api/v1/report/get").permitAll()
				.requestMatchers("/api/v1/report/").hasRole("rapor_kullanicisi")
				.anyRequest().authenticated()
				)
		     .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		     .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		// @formatter:on

		return http.build();
	}

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
