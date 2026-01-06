package com.techlambdas.invoice_generator.exception;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(String id) {
        super("Invoice not found with id: " + id);
    }
}

