package com.techlambdas.invoice_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvoiceGeneratorApplication {

	public static void main(String[] args) {

       System.out.println("MONGODB_URI from ENV = " + System.getenv("MONGODB_URI"));

		SpringApplication.run(InvoiceGeneratorApplication.class, args);
	}

}
