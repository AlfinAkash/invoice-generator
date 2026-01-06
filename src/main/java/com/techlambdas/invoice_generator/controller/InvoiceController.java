package com.techlambdas.invoice_generator.controller;
import com.techlambdas.invoice_generator.dto.InvoiceRequest;
import com.techlambdas.invoice_generator.dto.InvoiceResponse;
import com.techlambdas.invoice_generator.model.Invoice;
import com.techlambdas.invoice_generator.service.InvoiceService;
import com.techlambdas.invoice_generator.util.InvoicePdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInvoice(@RequestBody InvoiceRequest request) {
        invoiceService.createInvoice(request);
    }

    @GetMapping
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable String id) {
        return invoiceService.getInvoiceById(id);
    }

    @PutMapping("/{id}")
    public void updateInvoice(@PathVariable String id,
                              @RequestBody InvoiceRequest request) {
        invoiceService.updateInvoice(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable String id) {
        invoiceService.deleteInvoice(id);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable String id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        byte[] pdf = InvoicePdfGenerator.generate(invoice);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}


