package com.hotel.parceltrackingservice.service;

import com.hotel.parceltrackingservice.model.Guest;
import com.hotel.parceltrackingservice.model.Parcel;
import com.hotel.parceltrackingservice.repository.GuestRepository;
import com.hotel.parceltrackingservice.repository.ParcelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ParcelServiceTests {

    @Autowired
    private ParcelService parcelService;

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private GuestRepository guestRepository;

    private Guest guest;
    private Parcel parcel;

    @BeforeEach
    public void setUp() {
        // Clean up the database before each test
        parcelRepository.deleteAll();
        guestRepository.deleteAll();

        // Set up a guest
        guest = new Guest();
        guest.setFirstName("John");
        guest.setLastName("Doe");
        guest.setRoomNumber("101");
        guest.setCheckIn(LocalDateTime.now().minusDays(1));
        guest = guestRepository.save(guest);

        // Set up a parcel
        parcel = new Parcel();
        parcel.setParcelNumber("123456789");
        parcel.setGuest(guest);
        // Assume other fields are set up here
    }

    @Test
    public void testRegisterParcel() {
        // Given
        Parcel newParcel = parcelService.registerParcel(parcel);

        // Then
        if (newParcel == null || newParcel.getId() == null) {
            throw new AssertionError("Parcel was not registered successfully.");
        }
    }

    @Test
    public void testPickUpParcel() {
        // Given
        Parcel registeredParcel = parcelService.registerParcel(parcel);

        // When
        Parcel pickedUpParcel = parcelService.pickUpParcel(registeredParcel.getId());

        // Then
        if (!pickedUpParcel.isPickedUp()) {
            throw new AssertionError("Parcel was not marked as picked up.");
        }
    }

    @Test
    public void testFindAllParcelsForGuest() {
        // Given
        Parcel registeredParcel = parcelService.registerParcel(parcel);

        // When
        List<Parcel> parcelsForGuest = parcelService.findAllParcelsForGuest(guest.getId());

        // Then
        boolean found = false;
        for (Parcel p : parcelsForGuest) {
            if (p.getId().equals(registeredParcel.getId())) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Registered parcel was not found for the guest.");
        }
    }
}
