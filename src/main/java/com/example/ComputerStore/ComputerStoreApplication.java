package com.example.ComputerStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ComputerStoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(ComputerStoreApplication.class, args);
	}
}