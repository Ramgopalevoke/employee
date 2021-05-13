package com.evoke.employee.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.dom4j.DocumentException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.evoke.employee.entity.Employee;
import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;

public class EmployeePDFExporter implements EmployeeExporter {
    private List<Employee> listEmployees;
    private String docName;

    public EmployeePDFExporter(List<Employee> listUsers, String docName) {
        this.listEmployees = listUsers;
        this.docName = docName;
    }

    public PDDocument createPdf(List<Employee> empList) throws IOException {

        PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDRectangle rect = page.getMediaBox();
        document.addPage(page);

        PDPageContentStream cos = new PDPageContentStream(document, page);

        float margin = 20;
        float yStartNewPage = page.getMediaBox()
                .getHeight() - (2 * margin);
        float tableWidth = page.getMediaBox()
                .getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        float yPosition = 820;

        BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, drawContent);
        Row<PDPage> headerRow = table.createRow(30);
        Cell<PDPage> cell = headerRow.createCell(100, "Employees Data");
        cell.setFont(fontBold);
        cell.setFontSize(20);
        cell.setValign(VerticalAlignment.MIDDLE);
        cell.setAlign(HorizontalAlignment.CENTER);
        table.addHeaderRow(headerRow);

        Row<PDPage> row = table.createRow(20);
        cell = row.createCell(8f, "ID");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(27f, "E-mail");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(25f, "Full Name");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(20f, "PhoneNo");
        cell.setFontSize(10);
        cell.setFont(fontBold);
        cell = row.createCell(20f, "Department");
        cell.setFontSize(10);
        cell.setFont(fontBold);



        for (Employee emp : empList) {
            row = table.createRow(20);

            cell = row.createCell(8f, String.valueOf(emp.getId()));
            cell.setFontSize(10);

            cell = row.createCell(27f, String.valueOf(emp.getEmail()));
            cell.setFontSize(10);

            cell = row.createCell(25f, String.valueOf(emp.getName()));
            cell.setFontSize(10);

            cell = row.createCell(20f, String.valueOf(emp.getPhone()));
            cell.setFontSize(10);

            cell = row.createCell(20f, String.valueOf(emp.getDepartment()
                    .getDepName()));
            cell.setFontSize(10);

        }

        table.draw();
        cos.close();

        return document;

    }



    public ResponseEntity export() throws DocumentException, IOException {

        PDDocument doc = createPdf(listEmployees);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        doc.save(out);
        doc.close();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + docName + ".pdf")
                .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
    }
}
