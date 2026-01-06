package com.techlambdas.invoice_generator.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.techlambdas.invoice_generator.model.Company;
import com.techlambdas.invoice_generator.model.Invoice;
import com.techlambdas.invoice_generator.model.InvoiceItem;

import java.io.ByteArrayOutputStream;

public class InvoicePdfGenerator {

    public static byte[] generate(Invoice invoice) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" "));

            // Null-safe company
            Company company = invoice.getCompany();
            String companyName = (company != null && company.getName() != null) ? company.getName() : "Unknown Company";
            document.add(new Paragraph("Company: " + companyName));

            // Null-safe customer
            String customerName = (invoice.getCustomer() != null && invoice.getCustomer().getName() != null)
                    ? invoice.getCustomer().getName() : "Unknown Customer";
            document.add(new Paragraph("Customer: " + customerName));

            document.add(new Paragraph("Invoice Date: " + (invoice.getInvoiceDate() != null ? invoice.getInvoiceDate() : "N/A")));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            table.addCell("Item");
            table.addCell("Qty");
            table.addCell("Rate");
            table.addCell("Tax %");
            table.addCell("Total");

            if (invoice.getItems() != null) {
                for (InvoiceItem item : invoice.getItems()) {
                    double total = item.getQuantity() * item.getRate();
                    table.addCell(item.getItemName() != null ? item.getItemName() : "N/A");
                    table.addCell(String.valueOf(item.getQuantity()));
                    table.addCell(String.valueOf(item.getRate()));
                    table.addCell(String.valueOf(item.getTaxPercentage()));
                    table.addCell(String.valueOf(total));
                }
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Subtotal: " + invoice.getSubTotal()));
            document.add(new Paragraph("Tax: " + invoice.getTaxAmount()));
            document.add(new Paragraph("Grand Total: " + invoice.getGrandTotal()));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
