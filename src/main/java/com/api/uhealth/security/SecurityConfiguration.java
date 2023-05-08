package com.api.uhealth.security;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Key;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements WebMvcConfigurer {


    private final UserDetailsService userDetailsService;

    private final JWTAuthorizationFilter authorizationFilter;


    public SecurityConfiguration(UserDetailsService userDetailsService, JWTAuthorizationFilter authorizationFilter) {
        this.userDetailsService = userDetailsService;
        this.authorizationFilter = authorizationFilter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .exposedHeaders("*") // importante para obtener el header authorization
                .allowedHeaders("*");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        JWTAuthenticationFilter jwtAuthenticationFilter =  new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        System.out.println(authManager);

        return http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers( HttpMethod.POST , "/products/****").hasAuthority("administrador")
                .requestMatchers( HttpMethod.DELETE , "/products/****").hasAuthority("administrador")
                .anyRequest()
                .authenticated()


//                .anyRequest()
//                .authenticated()

//                .requestMatchers("/products/").hasRole("administrador")
//                .httpBasic()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authorizationFilter, JWTAuthenticationFilter.class)
//                .addFilter(jwtAuthenticationFilter)
//                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }





    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Key secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
    public static void main(String[] args) {
        System.out.println("pass: " + new BCryptPasswordEncoder().encode("12345678"));
    }
}
