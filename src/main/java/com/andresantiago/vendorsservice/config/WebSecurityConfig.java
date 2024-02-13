package com.andresantiago.vendorsservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomBasicAuthenticationFilter customBasicAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((request) -> request
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/app/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/test/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/app/test/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/app/vendors/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/vendors/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/app/test/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/app/vendors/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/app/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/test/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/vendors/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v1/**").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(customBasicAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "v2/api-docs",
            "/swagger-resources",
            "swagger-resources",
            "/swagger-resources/**",
            "swagger-resources/**",
            "/configuration/ui",
            "configuration/ui",
            "/configuration/security",
            "configuration/security",
            "/swagger-ui.html",
            "swagger-ui.html",
            "webjars/**",
            "/v3/api-docs/**",
            "v3/api-docs/**",
            "/swagger-ui/**",
            "swagger-ui/**",
            "/csa/api/token",
            "/actuator/**",
            "/health/**",
            "index.html",
            "/index.html",
            "/app/**",
            "/app/test/**",
            "/test/**",
            "/app/**",
            "/app/vendors/**",
            "/vendors/**",
            "http://localhost:8080/**",
            "http://localhost:8080/app/**",
            "http://localhost:8080/app/vendors/**"
    };

    //    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user1 = User.builder()
//                .username("vs_tech_challenge")
//                .password(encoder().encode("SuperSecurePassword123@"))
//                .roles("USER", "ADMIN")
//                .build();
//        UserDetails user2 = User.builder()
//                .username("andre")
//                .password(encoder().encode("123"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
}