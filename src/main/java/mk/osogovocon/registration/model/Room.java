package mk.osogovocon.registration.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import mk.osogovocon.registration.model.Guest;

import java.util.List;

@Entity
public class Room {

    @Id
    private String roomNumber;
    private int maxOccupants;
    private String bedConfiguration;
    private boolean isBooked;
    private String notes;
    // One-to-many relationship with Guest (one room can have many guests)
    @OneToMany(mappedBy = "room")
    private List<Guest> guests;

    public Room() {
    }

    public Room(String roomNumber, int maxOccupants, String bedConfiguration, boolean isBooked) {
        this.roomNumber = roomNumber;
        this.maxOccupants = maxOccupants;
        this.bedConfiguration = bedConfiguration;
        this.isBooked = isBooked;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getMaxOccupants() {
        return maxOccupants;
    }

    public String getBedConfiguration() {
        return bedConfiguration;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", maxOccupants=" + maxOccupants +
                ", bedConfiguration='" + bedConfiguration + '\'' +
                ", isBooked=" + isBooked +
                ", notes='" + notes + '\'' +
                '}';
    }
}
