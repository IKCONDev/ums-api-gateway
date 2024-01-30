package com.ikn.ums.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class UMSApiGatewayApplication {

	public static void main(String[] args) {
		log.info("main() entered with args "+args);
		SpringApplication.run(UMSApiGatewayApplication.class, args);
	}

}
