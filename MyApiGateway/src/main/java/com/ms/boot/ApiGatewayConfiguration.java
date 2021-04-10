package com.ms.boot;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/asdf")	
						.filters(f -> f
								.addRequestHeader("MyHeader", "MyURI")
								.addRequestParameter("Param", "MyValue"))
						.uri("http://httpbin.org:80"))
			
				.route(p -> p.path("/product/**") //.filters(//forward only of username is xyz)
						.uri("lb://productms"))
						
				.route(p -> p.path("/asdf/**")
						.filters(f -> f.rewritePath(
								"/asdf/(?<segment>.*)", 
								"/product/${segment}"))
						.uri("lb://productms"))
				
				.build();
	}

}