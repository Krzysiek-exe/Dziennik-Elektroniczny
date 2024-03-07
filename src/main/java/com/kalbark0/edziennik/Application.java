package com.kalbark0.edziennik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.kalbark0.edziennik")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
