package mk.osogovocon.registration.service;

import mk.osogovocon.registration.model.Room;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface ExcelExportService {
    Workbook generateExcel(List<Room> rooms);
}
