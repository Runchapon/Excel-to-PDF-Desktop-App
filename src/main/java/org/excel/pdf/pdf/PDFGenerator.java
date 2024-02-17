package org.excel.pdf.pdf;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class PDFGenerator {

    public static final String DEST = "iTextHelloWorld.pdf";
    public static final String ANGSANA_FONT_FILE = "ANGSA.ttf";

    protected PdfFont angsanaFont;

    public static void main(String[] args) throws RuntimeException, URISyntaxException, IOException {
        var pdf = new PDFGenerator();
        try {
            pdf.generate();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public PDFGenerator() throws URISyntaxException, IOException {
        URL fontUrl = getClass().getClassLoader().getResource(ANGSANA_FONT_FILE);
        String fontPath = Objects.requireNonNull(fontUrl).toURI().getPath();
        angsanaFont = PdfFontFactory.createFont(fontPath);
    }

    public void generate() throws IOException, URISyntaxException {

        PdfWriter pdfWriter = new PdfWriter(DEST);
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        pdfDoc.addNewPage(PageSize.A4);
        Document document = new Document(pdfDoc);

        // add header
        Paragraph header = new Paragraph();
        Text text = new Text("บิลค่าเช่าห้อง")
                .setFont(angsanaFont)
                .setFontSize(40);
        header.add(text);
        header.setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.BOTTOM);

        document.add(header);

        // add date time of invoice
        Table subHeader = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        subHeader.setBorder(Border.NO_BORDER);

        var monthLabel = getMonthLabel();
        subHeader.addCell(monthLabel);

        var monthYear = getMonthYear();
        subHeader.addCell(monthYear);

        document.add(subHeader);

        Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        for (int i = 0; i < 16; i++) {
            table.addCell("");
            table.addCell(new Cell().setHeight(10));
        }

        document.add(table);

        document.close();
    }

    private Cell getMonthYear() {
        Locale thai = new Locale("th", "TH");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM yyyy").withLocale(thai);
        String thaiDateFormat = ThaiBuddhistDate.now().format(format);
        return new Cell()
                .add(new Paragraph().add(thaiDateFormat)
                        .setFont(angsanaFont)
                        .setFontSize(16))
                .setHeight(40)
                .setBorder(Border.NO_BORDER);
    }

    private Cell getMonthLabel() {
        return new Cell()
                .add(new Paragraph("ประจำเดือน")
                        .setFont(angsanaFont)
                        .setFontSize(16))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setHeight(40)
                .setBorder(Border.NO_BORDER);
    }
}
