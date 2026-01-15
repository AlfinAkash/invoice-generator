 package com.techlambdas.invoice_generator.config;

 import com.mongodb.client.MongoClient;
 import com.mongodb.client.MongoClients;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.data.mongodb.core.MongoTemplate;

 @Configuration
 public class MongoConfig {

     @Bean
     public MongoClient mongoClient() {
         return MongoClients.create(
                 "add your mongodb atlas connection string here"
         );
     }

     @Bean
     public MongoTemplate mongoTemplate(MongoClient mongoClient) {
         return new MongoTemplate(mongoClient, "invoice_db");
     }
 }
