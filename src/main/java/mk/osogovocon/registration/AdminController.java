package mk.osogovocon.registration;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "admin";
    }

    @GetMapping("/export")
    @ResponseBody
    public void exportToExcel(HttpServletResponse response) throws IOException {

        // Fetch room data
        List<Room> rooms = roomService.getAllRooms();

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

        // Set the response headers for the download
        response.setHeader("Content-Disposition", "attachment; filename=rooms_and_guests.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // Write the Excel file to the output stream
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}