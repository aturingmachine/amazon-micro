package com.example.amazonorders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AmazonOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonOrdersApplication.class, args);
	}

	@Bean
	@LoadBalanced
	RestOperations restTemplate() {
		return new RestTemplate();
	}
}
