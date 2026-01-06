package com.techlambdas.invoice_generator.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {

    @Id
    private String id;

    private Company company;
    private Customer customer;

    private List<InvoiceItem> items;

    private double subTotal;
    private double taxAmount;
    private double grandTotal;

    private LocalDate invoiceDate;
}

