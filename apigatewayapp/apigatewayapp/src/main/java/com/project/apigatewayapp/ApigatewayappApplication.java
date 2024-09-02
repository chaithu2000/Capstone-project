package com.project.apigatewayapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class ApigatewayappApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApigatewayappApplication.class, args);
    }

    @Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			.route("customer-micro", r -> r.path("/customer/**")
				.uri("http://localhost:8081"))
			.route("product-micro", r -> r.host("/product/**")
				.uri("http://localhost:8082"))
			.build();
    }
}
