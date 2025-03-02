package mk.osogovocon.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoomDataLoader implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only add rooms if the database is empty
        if (roomRepository.count() == 0) {
            roomRepository.save(new Room("101", 2, "2 single bed", false));
            roomRepository.save(new Room("102", 2, "2 single bed", false));
            roomRepository.save(new Room("104", 2, "2 single bed", false));
            roomRepository.save(new Room("105", 2, "2 single bed", false));
            roomRepository.save(new Room("107", 2, "2 single bed", false));
            roomRepository.save(new Room("108", 2, "2 single bed", false));
            roomRepository.save(new Room("110", 2, "DOUBLE", false));
            roomRepository.save(new Room("111", 4, "DOUBLE + 2 SINGLE", false));
            roomRepository.save(new Room("112", 3, "DOUBLE + SINGLE", false));
            roomRepository.save(new Room("113", 3, "DOUBLE + SINGLE", false));
            roomRepository.save(new Room("114", 3, "DOUBLE + SINGLE", false));
            roomRepository.save(new Room("115", 5, "APARTMAN, 4 SINGLE + DOUBLE", false));
            roomRepository.save(new Room("201", 4, "DOUBLE + 2 SINGLE", false));
            roomRepository.save(new Room("202", 3, "DOUBLE + SINGLE", false));
            roomRepository.save(new Room("203", 3, "DOUBLE + SINGLE", false));
            roomRepository.save(new Room("204", 5, "APARTMAN 2 Double + 2 SINGLE", false));
            roomRepository.save(new Room("301", 5, "APARTMAN 2 Double + 2 SINGLE", false));
            roomRepository.save(new Room("302", 5, "APARTMAN 2 Double + 2 SINGLE", false));
            roomRepository.save(new Room("402", 4, "double + 2 single bed", false));
            roomRepository.save(new Room("403", 4, "DOUBLE + 2 SINGLE", false));
            roomRepository.save(new Room("501", 5, "APARTMAN 2 Double + 2 SINGLE", false));
            roomRepository.save(new Room("1", 3, "3 single bed", false));
            roomRepository.save(new Room("2", 2, "DOUBLE", false));
            roomRepository.save(new Room("3", 2, "DOUBLE", false));
            roomRepository.save(new Room("4", 3, "DOUBLE + SINGLE", false));
            roomRepository.save(new Room("5", 3, "DOUBLE + SINGLE", false));
            roomRepository.save(new Room("6", 2, "2 single bed", false));
            roomRepository.save(new Room("7", 3, "DOUBLE + SINGLE", false));
            roomRepository.save(new Room("8", 3, "DOUBLE + SINGLE", false));
        }
    }
}
