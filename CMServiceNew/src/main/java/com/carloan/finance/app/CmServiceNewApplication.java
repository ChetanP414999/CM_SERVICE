package com.carloan.finance.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CmServiceNewApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmServiceNewApplication.class, args);
	}
	
	@Bean
	public RestTemplate rt()
	{
		return new RestTemplate();
	}

}
