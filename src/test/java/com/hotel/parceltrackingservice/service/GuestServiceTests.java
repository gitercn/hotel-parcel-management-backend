package com.hotel.parceltrackingservice.service;

import com.hotel.parceltrackingservice.model.Guest;
import com.hotel.parceltrackingservice.repository.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class GuestServiceTests {

    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestRepository guestRepository;

    @BeforeEach
    public void setUp() {
        // Clean up the database before each test
        guestRepository.deleteAll();
    }

    @Test
    public void testFindAllCheckedInGuests() {
        // Given
        Guest guest = new Guest();
        guest.setFirstName("John");
        guest.setLastName("Doe");
        guest.setRoomNumber("101");
        guest.setCheckIn(LocalDateTime.now().minusDays(1));
        guestRepository.save(guest);

        // When
        List<Guest> checkedInGuests = guestService.findAllCheckedInGuests();

        // Then
        boolean found = false;
        for (Guest checkedInGuest : checkedInGuests) {
            if (checkedInGuest.getId().equals(guest.getId())) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Guest was not found in the list of checked-in guests.");
        }
    }

    @Test
    public void testCheckInGuest() {
        // Given
        Guest guest = new Guest();
        guest.setFirstName("Jane");
        guest.setLastName("Smith");
        guest.setRoomNumber("102");

        // When
        Guest checkedInGuest = guestService.checkInGuest(guest);

        // Then
        if (checkedInGuest.getCheckIn() == null) {
            throw new AssertionError("Guest check-in time was not set.");
        }
    }

    @Test
    public void testCheckOutGuest() {
        // Given
        Guest guest = new Guest();
        guest.setFirstName("Alice");
        guest.setLastName("Johnson");
        guest.setRoomNumber("103");
        guest.setCheckIn(LocalDateTime.now().minusDays(1));
        guest = guestRepository.save(guest);

        // When
        Guest checkedOutGuest = guestService.checkOutGuest(guest.getId());

        // Then
        if (checkedOutGuest.getCheckOut() == null) {
            throw new AssertionError("Guest check-out time was not set.");
        }
    }
}
