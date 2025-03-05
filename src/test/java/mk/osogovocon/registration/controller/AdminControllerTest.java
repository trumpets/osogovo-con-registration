package mk.osogovocon.registration.controller;

import mk.osogovocon.registration.service.impl.ExcelExportServiceImpl;
import mk.osogovocon.registration.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomServiceImpl roomService;

    @MockBean
    private ExcelExportServiceImpl excelExportService;

    @Test
    @WithMockUser
    void showAdminPage_ShouldReturnAdminView() throws Exception {
        when(roomService.getAllRooms()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin"));
    }
}
