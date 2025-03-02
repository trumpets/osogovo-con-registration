package mk.osogovocon.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {

    @Autowired
    private RoomService roomService;

    // Show the registration form with available room options
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Prepare the possible guest options (2, 3, 4, 5)
        int[] roomOptions = {2, 3, 4, 5};
        model.addAttribute("roomOptions", roomOptions);
        return "register";
    }

    // Show bed configurations based on the selected number of guests
    @PostMapping("/selectGuests")
    public String showAvailableBedConfigs(@RequestParam int numberOfGuests, Model model) {
        // Get available rooms for the selected number of guests
        List<Room> availableRooms = roomService.getAvailableRooms(numberOfGuests);

        // Filter out unique bed configurations for the selected number of guests
        List<String> bedConfigurations = availableRooms.stream()
                .map(Room::getBedConfiguration)
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("numberOfGuests", numberOfGuests);
        model.addAttribute("bedConfigurations", bedConfigurations);

        return "register"; // Return to the same page to allow selection of bed configuration
    }

    // Submit the registration form and auto-book the room based on selection
    @PostMapping("/submit")
    public String submitRegistrationForm(
            @RequestParam int numberOfGuests,
            @RequestParam String bedConfiguration,
            Model model) {

        // Automatically book the first available room that matches the number of guests and bed configuration
        Room bookedRoom = roomService.bookRoom(numberOfGuests, bedConfiguration);

        if (bookedRoom == null) {
            model.addAttribute("errorMessage", "No available room matches your selection.");
            return "register"; // Show error message
        }

        model.addAttribute("roomNumber", bookedRoom.getRoomNumber());
        model.addAttribute("numberOfGuests", numberOfGuests);
        model.addAttribute("bedConfiguration", bedConfiguration);

        return "confirmation"; // Show booking confirmation page
    }
}
