package mk.osogovocon.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${OSOGOVO_EMAIL_USER:your-email@gmail.com}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(fromEmail);
        emailSender.send(message);
    }

    public void sendBookingEmails(List<Guest> guests, Room room) {
        for (Guest guest : guests) {
            // Send email to the guest
            String guestMessage = String.format(
                    "Почитуван(а) %s %s,\n\n" +
                            "Ви благодариме за вашата резервација. Вашата резервација за соба %s е потврдена.\n\n" +
                            "Контакт за прашања: ivo.neskovic@gmail.com\n\n" +
                            "Се гледаме!,\n" +
                            "/ivo",
                    guest.getFirstName(), guest.getLastName(), room.getBedConfiguration()
            );

            sendEmail(guest.getEmail(), "Осогово Кон - Потврда за резервација", guestMessage);
        }

        String guestNames = guests.stream()
                .map(g -> g.getFirstName() + " " + g.getLastName())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        // Send email to admin (you)
        String adminMessage = String.format(
                "Нова резервација е направена.\n\n" +
                        "Име и презиме: %s\n" +
                        "Резервација за соба: %s\n\n" +
                guestNames, room.getBedConfiguration()
        );
        sendEmail("ivo.neskovic@gmail.com", "Осогово Кон - Нова резервација", adminMessage);
    }
}
