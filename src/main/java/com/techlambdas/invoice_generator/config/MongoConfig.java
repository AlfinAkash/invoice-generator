// package com.techlambdas.invoice_generator.config;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.mongodb.core.MongoTemplate;

// @Configuration
// public class MongoConfig {

//     @Bean
//     public MongoClient mongoClient() {
//         return MongoClients.create(
//                 "Need to add connection string here (If we use Atlas Cluster)) "
//         );
//     }

//     @Bean
//     public MongoTemplate mongoTemplate(MongoClient mongoClient) {
//         return new MongoTemplate(mongoClient, "invoice_db");
//     }
// }
