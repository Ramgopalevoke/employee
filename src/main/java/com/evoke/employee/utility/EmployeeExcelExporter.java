package com.evoke.employee.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.evoke.employee.entity.Employee;

public class EmployeeExcelExporter implements EmployeeExporter {
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private List<Employee> listEmployees;
    private String docName;

    public EmployeeExcelExporter(List<Employee> listUsers, String docName) {
        this.listEmployees = listUsers;
        this.docName = docName;
        workbook = new HSSFWorkbook();
    }


    private void writeHeaderLine() {

        sheet = workbook.createSheet("Employees");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        createCell(row, 0, "Employee ID", style);
        createCell(row, 1, "E-mail", style);
        createCell(row, 2, "Full Name", style);
        createCell(row, 3, "Phone", style);
        createCell(row, 4, "Department", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        style.setFont(font);

        for (Employee Employee : listEmployees) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, Employee.getId(), style);
            createCell(row, columnCount++, Employee.getEmail(), style);
            createCell(row, columnCount++, Employee.getName(), style);
            createCell(row, columnCount++, Employee.getPhone(), style);
            createCell(row, columnCount++, Employee.getDepartment()
                    .getDepName(), style);

        }
    }

    public ResponseEntity<InputStreamResource> export() throws IOException {

        writeHeaderLine();
        writeDataLines();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        out.close();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", "attachment; filename=" + docName + ".xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
    }
}
