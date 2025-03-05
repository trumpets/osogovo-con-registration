package mk.osogovocon.registration.repository;

import mk.osogovocon.registration.model.Guest;
import mk.osogovocon.registration.model.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class GuestRepositoryTest {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void shouldSaveGuest() {
        Room room = new Room("202", 2, "Single", false);
        roomRepository.save(room);
        Guest guest = new Guest("Jane", "Doe", "jane@example.com", "123456", room);
        guestRepository.save(guest);
        assertNotNull(guestRepository.findById(guest.getId()));
    }
}
