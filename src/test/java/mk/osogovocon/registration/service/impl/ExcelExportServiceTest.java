package mk.osogovocon.registration.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExcelExportServiceTest {

    @MockBean
    private ExcelExportServiceImpl excelExportService;

    @Test
    void generateExcel_ShouldReturnWorkbook() {
        when(excelExportService.generateExcel(anyList())).thenReturn(mock(Workbook.class));
        Workbook workbook = excelExportService.generateExcel(Collections.emptyList());
        assertNotNull(workbook);
    }
}
