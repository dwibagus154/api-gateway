package com.dwibagus.api.gateway.config;

import com.dwibagus.api.gateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://auth"))
                .route("post-collab", r -> r.path("/post/**").filters(f -> f.filter(filter)).uri("lb://post-collab"))
                .route("bank-account", r -> r.path("/bank-account/**").filters(f -> f.filter(filter)).uri("lb://bank-account-service"))
                .route("log-service", r -> r.path("/log/**").filters(f -> f.filter(filter)).uri("lb://log-service")).build();
    }
}