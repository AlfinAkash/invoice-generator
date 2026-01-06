package com.techlambdas.invoice_generator.model;

import lombok.Data;

@Data
public class Company {
    private String name;
    private String address;
    private String gstNumber;
}
