package mk.osogovocon.registration.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import mk.osogovocon.registration.model.Room;
import mk.osogovocon.registration.service.ExcelExportService;
import mk.osogovocon.registration.service.RoomService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    private final RoomService roomService;
    private final ExcelExportService excelExportService;

    public AdminController(RoomService roomService, ExcelExportService excelExportService) {
        this.roomService = roomService;
        this.excelExportService = excelExportService;
    }

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
        Workbook workbook = excelExportService.generateExcel(rooms);

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