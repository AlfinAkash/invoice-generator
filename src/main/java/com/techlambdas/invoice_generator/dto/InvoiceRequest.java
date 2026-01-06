package com.techlambdas.invoice_generator.dto;


import com.techlambdas.invoice_generator.model.Company;
import com.techlambdas.invoice_generator.model.Customer;
import com.techlambdas.invoice_generator.model.InvoiceItem;

import java.util.List;

public record InvoiceRequest(
        Company company,
        Customer customer,
        List<InvoiceItem> items
) {
}

