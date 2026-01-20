package com.warehouse.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    /* =====================================================
       ROUTING DEI MICROSERVIZI
       ===================================================== */
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        return builder.routes()

            // AUTH SERVICE
            .route("auth-service", r -> r
                .path("/auth/**")
                .uri("http://auth-service:8081")
            )

            // WAREHOUSE SERVICE
            .route("warehouse-service", r -> r
                .path("/warehouse/**")
                .uri("http://warehouse-service:8082")
            )

            // ORDER SERVICE
            .route("order-service", r -> r
                .path("/orders/**")
                .uri("http://order-service:8083")
            )

            // OFFICE / ANALYTICS SERVICE
            .route("office-service", r -> r
                .path("/office/**")
                .uri("http://office-service:8084")
            )
            .build();
    }
}


