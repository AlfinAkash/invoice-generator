package com.techlambdas.invoice_generator.repository;

import com.techlambdas.invoice_generator.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
}

