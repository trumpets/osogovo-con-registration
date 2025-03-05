package mk.osogovocon.registration.service;

import mk.osogovocon.registration.model.Guest;
import mk.osogovocon.registration.model.Room;

import java.util.List;

public interface EmailService {
    void sendEmail(String to, String subject, String text);

    void sendBookingEmails(List<Guest> guests, Room room);
}
