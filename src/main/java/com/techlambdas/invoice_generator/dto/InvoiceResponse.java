package com.techlambdas.invoice_generator.dto;


import java.time.LocalDate;

public record InvoiceResponse(
        String id,
        double subTotal,
        double taxAmount,
        double grandTotal,
        LocalDate invoiceDate
) {
}

