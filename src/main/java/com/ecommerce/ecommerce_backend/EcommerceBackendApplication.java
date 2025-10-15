package com.ecommerce.ecommerce_backend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class EcommerceBackendApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kathmandu"));
		SpringApplication.run(EcommerceBackendApplication.class, args);
		helper();
	}

	@PostConstruct
	public static void helper(){
		System.out.println("Default TimeZone = " + java.util.TimeZone.getDefault().getID());
	}


}
