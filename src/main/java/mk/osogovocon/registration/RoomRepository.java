package mk.osogovocon.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    // Query to find rooms by number of guests, bed configuration, and not booked
    List<Room> findByMaxOccupantsAndBedConfigurationAndIsBookedFalse(int maxOccupants, String bedConfiguration);

    // Query to find rooms by number of guests and not booked (ignores bed configuration)
    List<Room> findByMaxOccupantsAndIsBookedFalse(int maxOccupants);
}
