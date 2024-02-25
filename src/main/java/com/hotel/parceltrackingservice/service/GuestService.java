package com.hotel.parceltrackingservice.service;

import com.hotel.parceltrackingservice.model.Guest;
import com.hotel.parceltrackingservice.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GuestService {
    private static final Logger logger = LoggerFactory.getLogger(GuestService.class);
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> findAllCheckedInGuests() {
        // Fetch all guests and filter by the dynamic isCheckedIn method
        return guestRepository.findAll().stream()
                .filter(Guest::isCheckedIn)
                .collect(Collectors.toList());
    }

    public Guest checkInGuest(Guest guest) {
        // Set the check-in time to now
        guest.setCheckIn(LocalDateTime.now());
        // Save the updated guest
        return guestRepository.save(guest);
    }

    public Guest checkOutGuest(Long guestId) {
        // Find the guest by ID and set their check-out time if they're found
        Optional<Guest> guestOptional = guestRepository.findById(guestId);
        if (guestOptional.isPresent()) {
            Guest guest = guestOptional.get();
            guest.setCheckOut(LocalDateTime.now());
            return guestRepository.save(guest);
        } else {
            // Handle the case where the guest is not found
            logger.error("Guest not found with id: {}", guestId);
            throw new RuntimeException("Guest not found with id: " + guestId);
        }
    }

}
