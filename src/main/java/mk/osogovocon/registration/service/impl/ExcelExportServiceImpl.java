package mk.osogovocon.registration.service.impl;

import mk.osogovocon.registration.model.Guest;
import mk.osogovocon.registration.model.Room;
import mk.osogovocon.registration.service.ExcelExportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    @Override
    public Workbook generateExcel(List<Room> rooms) {

        // Create a new workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rooms and Guests");

        // Set up header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Соба бр");
        headerRow.createCell(1).setCellValue("Име и презиме");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Телефон");
        headerRow.createCell(4).setCellValue("Лица");
        headerRow.createCell(5).setCellValue("Тип Соба");
        headerRow.createCell(6).setCellValue("Notes");

        int rowNum = 1;

        // Loop over rooms and create rows for each room and its guests
        for (Room room : rooms) {
            String guestNames = "";
            String guestEmails = "";
            String guestPhoneNumbers = "";

            for (Guest guest : room.getGuests()) {
                guestNames += guest.getFirstName() + " " + guest.getLastName() + "\n";
                guestEmails += guest.getEmail() + "\n";
                guestPhoneNumbers += guest.getPhoneNumber() + "\n";
            }

            guestNames = guestNames.trim();
            guestEmails = guestEmails.trim();
            guestPhoneNumbers = guestPhoneNumbers.trim();

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(room.getRoomNumber());
            row.createCell(1).setCellValue(guestNames);
            row.createCell(2).setCellValue(guestEmails);
            row.createCell(3).setCellValue(guestPhoneNumbers);
            row.createCell(4).setCellValue(room.getMaxOccupants());
            row.createCell(5).setCellValue(room.getBedConfiguration());
            row.createCell(6).setCellValue(room.getNotes());
        }

        // Auto-size columns to fit the content
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }
}
