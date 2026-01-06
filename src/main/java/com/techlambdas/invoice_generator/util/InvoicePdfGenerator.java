package com.techlambdas.invoice_generator.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.techlambdas.invoice_generator.model.Company;
import com.techlambdas.invoice_generator.model.Invoice;
import com.techlambdas.invoice_generator.model.InvoiceItem;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

public class InvoicePdfGenerator {

    private static final Font TITLE_FONT =
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);

    private static final Font HEADER_FONT =
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);

    private static final Font NORMAL_FONT =
            FontFactory.getFont(FontFactory.HELVETICA, 10);

    private static final Font TABLE_HEADER_FONT =
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

    private static final DecimalFormat AMOUNT_FORMAT =
            new DecimalFormat("#,##0.00");

    public static byte[] generate(Invoice invoice) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(document, out);
            document.open();

            addHeader(document, invoice);
            addCustomerSection(document, invoice);
            addItemsTable(document, invoice);
            addTotalsSection(document, invoice);
            addFooter(document);

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate invoice PDF", e);
        }

        return out.toByteArray();
    }



    private static void addHeader(Document document, Invoice invoice)
            throws DocumentException {

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{70, 30});

        Company company = invoice.getCompany();
        String companyName =
                company != null && company.getName() != null
                        ? company.getName()
                        : "Company Name";

        PdfPCell companyCell = new PdfPCell();
        companyCell.setBorder(Rectangle.NO_BORDER);
        companyCell.addElement(new Paragraph(companyName, HEADER_FONT));
        companyCell.addElement(new Paragraph("Invoice Date: " +
                (invoice.getInvoiceDate() != null
                        ? invoice.getInvoiceDate().toString()
                        : "N/A"), NORMAL_FONT));

        PdfPCell titleCell = new PdfPCell(
                new Paragraph("INVOICE", TITLE_FONT));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        headerTable.addCell(companyCell);
        headerTable.addCell(titleCell);

        document.add(headerTable);
        document.add(Chunk.NEWLINE);
    }


    private static void addCustomerSection(Document document, Invoice invoice)
            throws DocumentException {

        String customerName =
                invoice.getCustomer() != null
                        && invoice.getCustomer().getName() != null
                        ? invoice.getCustomer().getName()
                        : "Customer Name";

        Paragraph customer = new Paragraph();
        customer.add(new Chunk("Bill To:\n", HEADER_FONT));
        customer.add(new Chunk(customerName, NORMAL_FONT));

        document.add(customer);
        document.add(Chunk.NEWLINE);
    }


    private static void addItemsTable(Document document, Invoice invoice)
            throws DocumentException {

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{40, 10, 15, 15, 20});

        addTableHeader(table, "Item");
        addTableHeader(table, "Qty");
        addTableHeader(table, "Rate");
        addTableHeader(table, "Tax %");
        addTableHeader(table, "Total");

        if (invoice.getItems() != null) {
            for (InvoiceItem item : invoice.getItems()) {

                double total = item.getQuantity() * item.getRate();

                table.addCell(createCell(item.getItemName(), Element.ALIGN_LEFT));
                table.addCell(createCell(String.valueOf(item.getQuantity()), Element.ALIGN_CENTER));
                table.addCell(createCell(formatAmount(item.getRate()), Element.ALIGN_RIGHT));
                table.addCell(createCell(formatAmount(item.getTaxPercentage()), Element.ALIGN_RIGHT));
                table.addCell(createCell(formatAmount(total), Element.ALIGN_RIGHT));
            }
        }

        document.add(table);
    }

    private static void addTableHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, TABLE_HEADER_FONT));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6);
        table.addCell(cell);
    }

    private static PdfPCell createCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "N/A", NORMAL_FONT));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(6);
        return cell;
    }


    private static void addTotalsSection(Document document, Invoice invoice)
            throws DocumentException {

        PdfPTable totals = new PdfPTable(2);
        totals.setWidthPercentage(40);
        totals.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totals.setSpacingBefore(10);
        totals.setWidths(new float[]{60, 40});

        addTotalRow(totals, "Subtotal", invoice.getSubTotal());
        addTotalRow(totals, "Tax", invoice.getTaxAmount());

        PdfPCell label = new PdfPCell(
                new Phrase("Grand Total", TABLE_HEADER_FONT));
        label.setPadding(6);

        PdfPCell value = new PdfPCell(
                new Phrase(formatAmount(invoice.getGrandTotal()), TABLE_HEADER_FONT));
        value.setHorizontalAlignment(Element.ALIGN_RIGHT);
        value.setPadding(6);

        totals.addCell(label);
        totals.addCell(value);

        document.add(totals);
    }

    private static void addTotalRow(PdfPTable table, String label, double amount) {
        table.addCell(new PdfPCell(
                new Phrase(label, NORMAL_FONT)));
        PdfPCell value = new PdfPCell(
                new Phrase(formatAmount(amount), NORMAL_FONT));
        value.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(value);
    }


    private static void addFooter(Document document)
            throws DocumentException {

        document.add(Chunk.NEWLINE);
        Paragraph footer = new Paragraph(
                "Thank you for your business!",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    }

    private static String formatAmount(double amount) {
        return AMOUNT_FORMAT.format(amount);
    }
}
