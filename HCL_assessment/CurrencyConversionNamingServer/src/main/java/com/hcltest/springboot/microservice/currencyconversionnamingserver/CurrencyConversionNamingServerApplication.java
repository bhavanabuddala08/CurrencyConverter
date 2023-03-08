package com.hcltest.springboot.microservice.currencyconversionnamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CurrencyConversionNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionNamingServerApplication.class, args);
	}

}
