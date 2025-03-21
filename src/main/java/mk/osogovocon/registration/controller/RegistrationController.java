package mk.osogovocon.registration.controller;

import mk.osogovocon.registration.model.Room;
import mk.osogovocon.registration.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {

    private final RoomService roomService;

    public RegistrationController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Method to get available number of guests based on rooms in the database
    @GetMapping("/booking")
    public String showRegistrationForm(Model model) {
        // Fetch all rooms
        List<Room> rooms = roomService.getAllAvailableRooms();

        // Get unique occupancies (2, 3, 4, 5)
        Set<Integer> availableOccupancies = rooms.stream()
                .map(Room::getMaxOccupants)
                .collect(Collectors.toSet());
        model.addAttribute("rooms", rooms);
        model.addAttribute("availableOccupancies", availableOccupancies);

        return "register";
    }

    @PostMapping("/guests")
    public String showGuestDetails(@RequestParam int numberOfGuests,
                                   @RequestParam String bedConfiguration,
                                   Model model) {

        try {
            // Find a room that matches occupancy and bed config
            Optional<Room> availableRoom = roomService.getFirstAvailableRoom(numberOfGuests, bedConfiguration);

            if (availableRoom.isEmpty()) {
                model.addAttribute("errorMessage", "No available rooms with the selected criteria.");
                return "register"; // Return to the booking page if no rooms are available
            }

            // Pass guest count to the next page
            model.addAttribute("guestCount", numberOfGuests);
            model.addAttribute("roomNumber", availableRoom.get().getRoomNumber());

            return "guest-details"; // Forward to guest details input page
        }  catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    @PostMapping("/confirm")
    public String confirmBooking(@RequestParam String roomNumber,
                                 @RequestParam List<String> firstName,
                                 @RequestParam List<String> lastName,
                                 @RequestParam List<String> email,
                                 @RequestParam List<String> phoneNumber,
                                 @RequestParam String notes,
                                 Model model) {

        // TODO also get the notes for the room, either here or in the previous step

        try {
            Room room = roomService.bookRoom(roomNumber, firstName, lastName, email, phoneNumber, notes);

            model.addAttribute("roomNumber", room.getBedConfiguration());
            return "booking-confirmation";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }
}
