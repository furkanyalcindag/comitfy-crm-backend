package com.comitfy.fair;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FairApplication {


	public static void main(String[] args) {
		SpringApplication.run(FairApplication.class, args);

	}

}
