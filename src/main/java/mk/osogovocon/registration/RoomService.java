package mk.osogovocon.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Get available rooms based on the number of guests
    public List<Room> getAvailableRooms(int numberOfGuests) {
        return roomRepository.findByMaxOccupantsAndIsBookedFalse(numberOfGuests);
    }

    // Get available rooms based on the number of guests and bed configuration
    public List<Room> getAvailableRooms(int numberOfGuests, String bedConfiguration) {
        return roomRepository.findByMaxOccupantsAndBedConfigurationAndIsBookedFalse(numberOfGuests, bedConfiguration);
    }

    // Automatically book the first available room that matches the number of guests and bed configuration
    public Room bookRoom(int numberOfGuests, String bedConfiguration) {
        List<Room> availableRooms = getAvailableRooms(numberOfGuests, bedConfiguration);

        if (availableRooms.isEmpty()) {
            return null; // No available room found matching the selection
        }

        Room bookedRoom = availableRooms.get(0); // Get the first available room
        bookedRoom.setBooked(true); // Mark the room as booked
        roomRepository.save(bookedRoom); // Save the updated room to the repository

        return bookedRoom;
    }
}
