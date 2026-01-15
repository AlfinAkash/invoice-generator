package com.techlambdas.invoice_generator.repository;

import com.techlambdas.invoice_generator.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
