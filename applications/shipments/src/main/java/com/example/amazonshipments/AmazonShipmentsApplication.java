package com.example.amazonshipments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class AmazonShipmentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonShipmentsApplication.class, args);
	}

	@Bean
  @LoadBalanced
  RestOperations restOperations() {
	  return new RestTemplate();
  }
}
