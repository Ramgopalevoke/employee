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

    public EmployeePDFExporter(List<Employee> listUsers) {
        this.listEmployees = listUsers;
    }



    public void export(HttpServletResponse response) throws DocumentException, IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        doc.save("test.pdf");
    }
}
