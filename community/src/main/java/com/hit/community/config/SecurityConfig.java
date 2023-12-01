package com.hit.community.config;

import com.hit.community.config.jwt.JwtAuthFilter;
import com.hit.community.config.jwt.JwtExceptionFilter;
import com.hit.community.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.lang.invoke.MethodType;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler auth2SuccessHandlers;
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtExceptionFilter jwtExceptionFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .httpBasic(httpBasic->httpBasic.disable())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(config -> config
                        .requestMatchers(new MvcRequestMatcher(introspector, "/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/", HttpMethod.POST.name())).authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login(login->
                        login.userInfoEndpoint(
                                endpoint->endpoint.userService(customOAuth2UserService)
                                ).successHandler(auth2SuccessHandlers)

                ).logout(logout -> logout.logoutSuccessUrl("/"));

        return http
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
