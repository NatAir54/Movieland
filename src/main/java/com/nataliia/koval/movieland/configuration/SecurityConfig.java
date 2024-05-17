package com.nataliia.koval.movieland.configuration;

import com.nataliia.koval.movieland.service.impl.CustomUserDetailsService;
import com.nataliia.koval.movieland.web.controller.filter.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthFilter authFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(HttpMethod.POST, "/api/v1/reviews").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/api/v1/movies/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/genres/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/logout").permitAll()
                .anyRequest().authenticated()
        );
        http.userDetailsService(userDetailsService);
        return http.build();
    }
}
