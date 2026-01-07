package com.techlambdas.invoice_generator.controller;

import com.techlambdas.invoice_generator.dto.InvoiceRequest;
import com.techlambdas.invoice_generator.model.Invoice;
import com.techlambdas.invoice_generator.service.InvoiceService;
import com.techlambdas.invoice_generator.util.InvoicePdfGenerator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(
            @RequestBody InvoiceRequest request) {

        Invoice invoice = invoiceService.createInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(
            @PathVariable String id,
            @RequestBody InvoiceRequest request) {

        Invoice invoice = invoiceService.updateInvoice(id, request);
        return ResponseEntity.ok(invoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable String id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("Invoice deleted successfully");
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable String id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        byte[] pdf = InvoicePdfGenerator.generate(invoice);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice-" + invoice.getId() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
