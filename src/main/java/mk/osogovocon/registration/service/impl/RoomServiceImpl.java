package mk.osogovocon.registration.service.impl;

import mk.osogovocon.registration.model.Guest;
import mk.osogovocon.registration.model.Room;
import mk.osogovocon.registration.repository.GuestRepository;
import mk.osogovocon.registration.repository.RoomRepository;
import mk.osogovocon.registration.service.EmailService;
import mk.osogovocon.registration.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final EmailService emailService;

    public RoomServiceImpl(RoomRepository roomRepository, GuestRepository guestRepository, EmailService emailService) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.emailService = emailService;
    }

    @Override
    public List<Room> getAllAvailableRooms() {
        return roomRepository.findByIsBookedFalse();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get available rooms based on the number of guests and bed configuration
    @Override
    public Optional<Room> getFirstAvailableRoom(int numberOfGuests, String bedConfiguration) {
        return roomRepository.findFirstByMaxOccupantsAndBedConfigurationAndIsBookedFalse(numberOfGuests, bedConfiguration);
    }

    @Override
    public Room bookRoom(String roomNumber,
                         List<String> firstNames,
                         List<String> lastNames,
                         List<String> emails,
                         List<String> phoneNumbers,
                         String notes) {

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
        room.setNotes(notes);
        roomRepository.save(room);

        // TODO uncomment this when ready
//        emailService.sendBookingEmails(guests, room);

        return room;
    }
}
