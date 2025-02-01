package com.grelory.quickbill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final CorsConfigurationSource corsConfigurationSource;
    private final AuthenticationProvider authenticationProvider;
    private final UnauthorizedHandler unauthorizedHandler;
    private final DeniedAccessHandler accessDeniedHandler;

    public SecurityConfiguration(
            @Autowired UnauthorizedHandler unauthorizedHandler,
            @Autowired DeniedAccessHandler accessDeniedHandler,
            @Autowired JwtFilter jwtFilter,
            @Autowired CorsConfigurationSource corsConfigurationSource,
            @Autowired AuthenticationProvider authenticationProvider) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.accessDeniedHandler = accessDeniedHandler;
        this.jwtFilter = jwtFilter;
        this.corsConfigurationSource = corsConfigurationSource;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/auth/**", "/public/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAuthority("USER")
                        .anyRequest().denyAll())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
