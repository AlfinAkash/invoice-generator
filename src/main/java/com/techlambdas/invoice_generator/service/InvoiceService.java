package com.techlambdas.invoice_generator.service;
import com.techlambdas.invoice_generator.dto.InvoiceRequest;
import com.techlambdas.invoice_generator.dto.InvoiceResponse;
import com.techlambdas.invoice_generator.exception.InvoiceNotFoundException;
import com.techlambdas.invoice_generator.model.Invoice;
import com.techlambdas.invoice_generator.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public void createInvoice(InvoiceRequest request) {
        if (request.company() == null) {
            throw new IllegalArgumentException("Company information is required");
        }
        Invoice invoice = buildInvoice(request);
        invoiceRepository.save(invoice);
    }


    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Invoice getInvoiceById(String id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(id));
    }

    public void updateInvoice(String id, InvoiceRequest request) {
        Invoice existing = getInvoiceById(id);
        Invoice updated = buildInvoice(request);
        updated.setId(existing.getId());
        invoiceRepository.save(updated);
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

    private InvoiceResponse mapToResponse(Invoice invoice) {
        return new InvoiceResponse(
                invoice.getId(),
                invoice.getSubTotal(),
                invoice.getTaxAmount(),
                invoice.getGrandTotal(),
                invoice.getInvoiceDate()
        );
    }
}
