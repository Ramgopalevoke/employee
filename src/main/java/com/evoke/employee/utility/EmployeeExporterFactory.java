package com.evoke.employee.utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentException;
import com.evoke.employee.entity.Employee;

public class EmployeeExporterFactory {
    private List<Employee> listEmployees;
    private String type;

    public EmployeeExporterFactory(List<Employee> listUsers, String type) {
        this.listEmployees = listUsers;
        this.type = type;
    }



    public void export(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String DocName = "employees_" + currentDateTime;

        switch (type) {
            case "PDF":
                new EmployeePDFExporter(listEmployees, DocName);
                break;
            case "excel":
                new EmployeeExcelExporter(listEmployees, DocName);
                break;
        }
    }
}
