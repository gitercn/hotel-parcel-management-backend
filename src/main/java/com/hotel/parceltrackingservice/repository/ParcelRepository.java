package com.hotel.parceltrackingservice.repository;

import com.hotel.parceltrackingservice.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    // Add a custom method to find all parcels for a specific guest by their ID
    List<Parcel> findByGuestId(Long guestId);
}
