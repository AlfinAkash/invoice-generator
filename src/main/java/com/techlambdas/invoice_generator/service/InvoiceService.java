package com.techlambdas.invoice_generator.service;

import com.techlambdas.invoice_generator.dto.InvoiceRequest;
import com.techlambdas.invoice_generator.exception.InvoiceNotFoundException;
import com.techlambdas.invoice_generator.model.Invoice;
import com.techlambdas.invoice_generator.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice createInvoice(InvoiceRequest request) {

        if (request.company() == null || request.customer() == null) {
            throw new RuntimeException("Company and Customer information are required");
        }

        Invoice invoice = buildInvoice(request);
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(String id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(id));
    }

    public Invoice updateInvoice(String id, InvoiceRequest request) {
        Invoice existing = getInvoiceById(id);

        Invoice updated = buildInvoice(request);
        updated.setId(existing.getId());

        return invoiceRepository.save(updated);
    }

    public void deleteInvoice(String id) {
        Invoice invoice = getInvoiceById(id);
        invoiceRepository.delete(invoice);
    }

    private Invoice buildInvoice(InvoiceRequest request) {

        double subTotal = request.items().stream()
                .mapToDouble(i -> i.getQuantity() * i.getRate())
                .sum();

        double taxAmount = request.items().stream()
                .mapToDouble(i ->
                        (i.getQuantity() * i.getRate()) * i.getTaxPercentage() / 100
                ).sum();

        return Invoice.builder()
                .company(request.company())
                .customer(request.customer())
                .items(request.items())
                .subTotal(subTotal)
                .taxAmount(taxAmount)
                .grandTotal(subTotal + taxAmount)
                .invoiceDate(LocalDate.now())
                .build();
    }
}
