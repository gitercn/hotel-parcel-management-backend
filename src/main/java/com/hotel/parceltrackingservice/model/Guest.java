package com.hotel.parceltrackingservice.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "guests")
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    public boolean isCheckedIn() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        if (checkIn != null && now.isAfter(checkIn)) {
            return checkOut == null || now.isBefore(checkOut);
        }
        return false;
    }
}
