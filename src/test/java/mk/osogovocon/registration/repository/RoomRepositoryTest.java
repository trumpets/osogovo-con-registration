package mk.osogovocon.registration.repository;

import mk.osogovocon.registration.model.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void shouldFindAvailableRooms() {
        Room room = new Room("201", 2, "Double", false);
        roomRepository.save(room);
        List<Room> availableRooms = roomRepository.findByIsBookedFalse();
        assertFalse(availableRooms.isEmpty());
    }
}
