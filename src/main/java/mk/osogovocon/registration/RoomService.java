package mk.osogovocon.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GuestRepository guestRepository;

    public List<Room> getAllAvailableRooms() {
        return roomRepository.findByIsBookedFalse();
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get available rooms based on the number of guests and bed configuration
    public Optional<Room> getFirstAvailableRoom(int numberOfGuests, String bedConfiguration) {
        return roomRepository.findFirstByMaxOccupantsAndBedConfigurationAndIsBookedFalse(numberOfGuests, bedConfiguration);
    }

    public Room bookRoom(String roomNumber,
                         List<String> firstNames,
                         List<String> lastNames,
                         List<String> emails,
                         List<String> phoneNumbers) {

        if (firstNames.size() != lastNames.size() || firstNames.size() != emails.size() || firstNames.size() != phoneNumbers.size()) {
            throw new IllegalArgumentException("All lists must have the same size");
        }

        // Fetch the selected room
        Optional<Room> roomOpt = roomRepository.findByRoomNumber(roomNumber);
        if (roomOpt.isEmpty()) {
            throw new RuntimeException("Room not found");
        }

        Room room = roomOpt.get();

        // Create guest objects (assuming you have a Guest entity)
        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < firstNames.size(); i++) {
            Guest guest = new Guest(firstNames.get(i), lastNames.get(i), emails.get(i), phoneNumbers.get(i), room);
            guests.add(guest);
        }

        // Save guests and mark room as booked
        guestRepository.saveAll(guests);
        room.setBooked(true);
        roomRepository.save(room);

        return room;
    }
}
