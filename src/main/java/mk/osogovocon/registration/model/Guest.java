package mk.osogovocon.registration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Guest {
    private String firstName;
    private String lastName;
    @Id
    private String email;
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public Guest() {
    }

    public Guest(String firstName, String lastName, String email, String phoneNumber, Room room) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.room = room;
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Room getRoom() {
        return room;
    }
}
