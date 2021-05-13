package com.evoke.employee.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.evoke.employee.entity.Employee;

public class EmployeeExporterFactory {
    private List<Employee> listEmployees;
    private String type;

    public EmployeeExporterFactory(List<Employee> listUsers, String type) {
        this.listEmployees = listUsers;
        this.type = type;
    }



    public EmployeeExporter getExporter() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String DocName = "employees_" + currentDateTime;
        switch (type) {
            case "PDF":
                return new EmployeePDFExporter(listEmployees, DocName);
            case "excel":
                return new EmployeeExcelExporter(listEmployees, DocName);
        }
        return null;
    }
}
