package com.viewmore.poksin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PoksinApplication {

public static void main(String[] args) {
		SpringApplication.run(PoksinApplication.class, args);
	}

}
