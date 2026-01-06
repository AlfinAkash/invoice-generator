package com.techlambdas.invoice_generator.model;

import lombok.Data;

@Data
public class InvoiceItem {
    private String itemName;
    private int quantity;
    private double rate;
    private double taxPercentage;
}

