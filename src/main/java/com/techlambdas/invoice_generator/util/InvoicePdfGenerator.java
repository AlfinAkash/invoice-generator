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
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);

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
            addCompanyAndCustomerSection(document, invoice);
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

        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new float[]{70, 30});

        Paragraph left = new Paragraph();
        left.add(new Chunk(
                invoice.getCompany().getName() + "\n", HEADER_FONT));
        left.add(new Chunk(
                invoice.getCompany().getAddress() + "\n", NORMAL_FONT));
        left.add(new Chunk(
                "GST: " + invoice.getCompany().getGstNumber(), NORMAL_FONT));

        PdfPCell leftCell = new PdfPCell(left);
        leftCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell rightCell = new PdfPCell(
                new Paragraph("INVOICE", TITLE_FONT));
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        header.addCell(leftCell);
        header.addCell(rightCell);

        document.add(header);
        document.add(Chunk.NEWLINE);
    }



    private static void addCompanyAndCustomerSection(Document document, Invoice invoice)
            throws DocumentException {

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{50, 50});

        PdfPCell billTo = new PdfPCell();
        billTo.addElement(new Paragraph("Bill To", HEADER_FONT));
        billTo.addElement(new Paragraph(invoice.getCustomer().getName(), NORMAL_FONT));
        billTo.addElement(new Paragraph(invoice.getCustomer().getEmail(), NORMAL_FONT));
        billTo.addElement(new Paragraph(invoice.getCustomer().getAddress(), NORMAL_FONT));
        billTo.setPadding(8);

        PdfPCell invoiceInfo = new PdfPCell();
        invoiceInfo.addElement(new Paragraph("Invoice Date: "
                + invoice.getInvoiceDate(), NORMAL_FONT));
        invoiceInfo.addElement(new Paragraph("Invoice ID: "
                + invoice.getId(), NORMAL_FONT));
        invoiceInfo.setPadding(8);

        table.addCell(billTo);
        table.addCell(invoiceInfo);

        document.add(table);
        document.add(Chunk.NEWLINE);
    }



    private static void addItemsTable(Document document, Invoice invoice)
            throws DocumentException {

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{40, 10, 15, 15, 20});
        table.setSpacingBefore(10);

        addTableHeader(table, "Item");
        addTableHeader(table, "Qty");
        addTableHeader(table, "Rate");
        addTableHeader(table, "Tax %");
        addTableHeader(table, "Total");

        for (InvoiceItem item : invoice.getItems()) {
            double total = item.getQuantity() * item.getRate();

            table.addCell(createCell(item.getItemName(), Element.ALIGN_LEFT));
            table.addCell(createCell(String.valueOf(item.getQuantity()), Element.ALIGN_CENTER));
            table.addCell(createCell(format(item.getRate()), Element.ALIGN_RIGHT));
            table.addCell(createCell(format(item.getTaxPercentage()), Element.ALIGN_RIGHT));
            table.addCell(createCell(format(total), Element.ALIGN_RIGHT));
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
        PdfPCell cell = new PdfPCell(new Phrase(text, NORMAL_FONT));
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
                new Phrase(format(invoice.getGrandTotal()), TABLE_HEADER_FONT));
        value.setHorizontalAlignment(Element.ALIGN_RIGHT);
        value.setPadding(6);

        totals.addCell(label);
        totals.addCell(value);

        document.add(totals);
    }

    private static void addTotalRow(PdfPTable table, String label, double amount) {
        table.addCell(new PdfPCell(new Phrase(label, NORMAL_FONT)));
        PdfPCell value = new PdfPCell(new Phrase(format(amount), NORMAL_FONT));
        value.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(value);
    }



    private static void addFooter(Document document)
            throws DocumentException {

        document.add(Chunk.NEWLINE);


        Paragraph thankYou = new Paragraph(
                "Thank you for your business!",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9));
        thankYou.setAlignment(Element.ALIGN_CENTER);
        document.add(thankYou);


        Paragraph copyright = new Paragraph(
                "© 2026 AlfinAkash. All rights reserved.",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9));
        copyright.setAlignment(Element.ALIGN_CENTER);
        document.add(copyright);
    }


    private static String format(double value) {
        return AMOUNT_FORMAT.format(value);
    }
}
