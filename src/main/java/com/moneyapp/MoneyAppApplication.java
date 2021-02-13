package com.moneyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //Necesario para habilitar el filtro jwt de seguridad
@SpringBootApplication
public class MoneyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyAppApplication.class, args);
	}

}
