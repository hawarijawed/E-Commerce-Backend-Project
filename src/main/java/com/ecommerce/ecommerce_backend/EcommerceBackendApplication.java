package com.ecommerce.ecommerce_backend;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class EcommerceBackendApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
		System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("SMTP_USERNAME", dotenv.get("SMTP_USERNAME"));
		System.setProperty("SMTP_KEY", dotenv.get("SMTP_KEY"));
		System.setProperty("SECRET_KEY", dotenv.get("SECRET_KEY"));
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kathmandu"));
		SpringApplication.run(EcommerceBackendApplication.class, args);
		//helper();
	}

	@PostConstruct
	public static void helper(){
		System.out.println("Default TimeZone = " + java.util.TimeZone.getDefault().getID());
	}
    /*
    	A) Wishlist Module
		B) Search + Filter Module
		C) JWT Authentication
		D) Reviews & Ratings
		E) Address Module
     */

}
