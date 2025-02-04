package com.kbhc.board.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers( "/"
                                        ,"/swagger-ui.html"
                                        ,"/swagger-ui/**"
                                        ,"/webjars/**"
                                        ,"/v2/**"
                                        ,"/v3/**"
                                        ,"/swagger-resources"
                                        ,"/swagger-resources/**"
                                        ,"/configuration/**"
                                        ,"/docs"
                                        ,"/health-check"
                                        ,"/api/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
