package com.bsep.marketingacency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MarketingAcencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketingAcencyApplication.class, args);
	}

}
