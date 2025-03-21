package mk.osogovocon.registration.service.impl;

import mk.osogovocon.registration.model.Room;
import mk.osogovocon.registration.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    private Room sampleRoom;

    @BeforeEach
    void setUp() {
        sampleRoom = new Room("101", 2, "2 single bed", false);
    }

    @Test
    void getAllAvailableRooms_ShouldReturnRooms() {
        when(roomRepository.findByIsBookedFalse()).thenReturn(Collections.singletonList(sampleRoom));
        List<Room> result = roomService.getAllAvailableRooms();
        assertEquals(1, result.size());
    }

    @Test
    void bookRoom_ShouldThrowException_WhenRoomNotFound() {
        when(roomRepository.findByRoomNumber("101")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () ->
                roomService.bookRoom("101", List.of("John"), List.of("Doe"), List.of("john@example.com"), List.of("123456"), "note"));
        assertEquals("Room not found", exception.getMessage());
    }
}
