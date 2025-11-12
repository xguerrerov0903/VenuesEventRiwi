package com.xguerrerov.venues;

import org.springframework.boot.SpringApplication;

public class TestVenuesApplication {

	public static void main(String[] args) {
		SpringApplication.from(VenuesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
