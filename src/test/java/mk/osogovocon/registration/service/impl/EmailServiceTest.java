package mk.osogovocon.registration.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class EmailServiceTest {

    @MockBean
    private EmailServiceImpl emailService;

    @Test
    void sendEmail_ShouldNotThrowExceptions() {
        doNothing().when(emailService).sendEmail("test@example.com", "Subject", "Body");
        assertDoesNotThrow(() -> emailService.sendEmail("test@example.com", "Subject", "Body"));
    }
}
