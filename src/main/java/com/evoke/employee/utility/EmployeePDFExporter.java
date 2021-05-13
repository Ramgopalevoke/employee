package com.evoke.employee.utility;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.dom4j.DocumentException;
import com.evoke.employee.entity.Employee;

public class EmployeePDFExporter {
    private List<Employee> listEmployees;
    private String docName;

    public EmployeePDFExporter(List<Employee> listUsers, String docName) {
        this.listEmployees = listUsers;
        this.docName = docName;
    }



    public void export(HttpServletResponse response) throws DocumentException, IOException {
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + docName + ".pdf";
        response.setHeader(headerKey, headerValue);

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        doc.save("test.pdf");
    }
}
