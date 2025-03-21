package mk.osogovocon.registration.repository;

import mk.osogovocon.registration.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    Optional<Room> findFirstByMaxOccupantsAndBedConfigurationAndIsBookedFalse(int maxOccupants, String bedConfiguration);

    List<Room> findByIsBookedFalse();

    Optional<Room> findByRoomNumber(String roomNumber);
}
