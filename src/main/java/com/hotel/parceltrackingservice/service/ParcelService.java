package com.hotel.parceltrackingservice.service;

import com.hotel.parceltrackingservice.model.Guest;
import com.hotel.parceltrackingservice.model.Parcel;
import com.hotel.parceltrackingservice.repository.GuestRepository;
import com.hotel.parceltrackingservice.repository.ParcelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelService {

    private static final Logger logger = LoggerFactory.getLogger(ParcelService.class);
    private final ParcelRepository parcelRepository;
    private final GuestRepository guestRepository; // Add a reference to the GuestRepository

    @Autowired
    public ParcelService(ParcelRepository parcelRepository, GuestRepository guestRepository) {
        this.parcelRepository = parcelRepository;
        this.guestRepository = guestRepository; // Initialize the GuestRepository
    }

    public Parcel registerParcel(Parcel parcel) {
        // Retrieve the guest from the database
        Optional<Guest> guestOptional = guestRepository.findById(parcel.getGuest().getId());
        if (!guestOptional.isPresent() || !guestOptional.get().isCheckedIn()) {
            logger.error("Cannot register parcel. Guest is not currently checked in or does not exist.");
            throw new RuntimeException("Cannot register parcel. Guest is not currently checked in or does not exist.");
        }
        // If the guest is checked in, save the new parcel to the database
        return parcelRepository.save(parcel);
    }


    public Parcel pickUpParcel(Long parcelId) {
        // Find the parcel by ID
        Optional<Parcel> parcelOptional = parcelRepository.findById(parcelId);
        if (parcelOptional.isPresent()) {
            Parcel parcel = parcelOptional.get();
            parcel.setPickedUp(true);
            return parcelRepository.save(parcel);
        } else {
            logger.error("Parcel not found with id: {}", parcelId );
            throw new RuntimeException("Parcel not found with id: " + parcelId);
        }
    }

    public List<Parcel> findAllParcelsForGuest(Long guestId) {
        return parcelRepository.findByGuestId(guestId);
    }
}
