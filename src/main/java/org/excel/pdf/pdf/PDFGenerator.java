package org.excel.pdf.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.awt.*;
import java.beans.Encoder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;

public class PDFGenerator {

    public static void main(String[] args) throws RuntimeException {
        var pdf = new PDFGenerator();
        try {
            pdf.generate();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void generate() throws IOException, URISyntaxException {

        PdfWriter pdfWriter = new PdfWriter("iTextHelloWorld.pdf");
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        pdfDoc.addNewPage(PageSize.A4);
        Document document = new Document(pdfDoc);

        URL fontUrl = getClass().getClassLoader().getResource("ANGSA.ttf");
        String fontPath = Objects.requireNonNull(fontUrl).toURI().getPath();
        System.out.println(fontPath);
        PdfFont font = PdfFontFactory.createFont(fontPath);
        Paragraph header = new Paragraph();
        Text text = new Text("บิลค่าเช่าห้อง")
                .setFont(font)
                .setFontSize(20);
        header.add(text);

        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            PdfPage pdfPage = pdfDoc.getPage(i);
            // When "true": in case the page has a rotation, then new content will be automatically rotated in the
            // opposite direction. On the rotated page this would look as if new content ignores page rotation.
            pdfPage.setIgnorePageRotationForContent(true);

            Rectangle pageSize = pdfPage.getPageSize();
            float x, y;
            if (pdfPage.getRotation() % 180 == 0) {
                x = pageSize.getWidth() / 2;
                y = pageSize.getTop() - 40;
            } else {
                x = pageSize.getHeight() / 2;
                y = pageSize.getRight() - 20;
            }
            document.showTextAligned(header, x, y, i, TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0);
        }

        // Creating a table
//        float [] pointColumnWidths = {150F, 150F};
//        Table table = new Table(pointColumnWidths);
//
//        // Adding cells to the table
//        table.addCell(new Cell().add(new Paragraph("Name")));
//        table.addCell(new Cell().add(new Paragraph("Raju")));
//        table.addCell(new Cell().add(new Paragraph("Id")));
//        table.addCell(new Cell().add(new Paragraph("1001")));
//        table.addCell(new Cell().add(new Paragraph("Designation")));
//        table.addCell(new Cell().add(new Paragraph("Programmer")));
//
//        document.add(table);

        document.close();
    }

    public static String convertToUnicode(String input) {
        StringBuilder sb = new StringBuilder();

        for (char c : input.toCharArray()) {
            sb.append("\\u").append(Integer.toHexString((int) c));
        }

        return sb.toString();
    }
}
