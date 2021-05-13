package com.evoke.employee.utility;

import java.io.IOException;
import org.dom4j.DocumentException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface EmployeeExporter {

    public ResponseEntity<InputStreamResource> export() throws DocumentException, IOException;
}
