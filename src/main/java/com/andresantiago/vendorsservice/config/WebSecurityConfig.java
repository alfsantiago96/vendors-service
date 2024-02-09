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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                        .requestMatchers(HttpMethod.POST, "/v1/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v1/**").permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("**swagger-ui/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("**/swagger-ui/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("v3/api-docs/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
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
            "/health/**"
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