package mk.osogovocon.registration.service;

import mk.osogovocon.registration.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> getAllAvailableRooms();

    List<Room> getAllRooms();

    // Get available rooms based on the number of guests and bed configuration
    Optional<Room> getFirstAvailableRoom(int numberOfGuests, String bedConfiguration);

    Room bookRoom(String roomNumber,
                  List<String> firstNames,
                  List<String> lastNames,
                  List<String> emails,
                  List<String> phoneNumbers,
                  String notes);
}
