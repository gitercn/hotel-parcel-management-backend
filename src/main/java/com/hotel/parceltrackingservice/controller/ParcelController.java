package com.hotel.parceltrackingservice.controller;

import com.hotel.parceltrackingservice.model.Parcel;
import com.hotel.parceltrackingservice.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {

    private final ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerParcel(@RequestBody Parcel parcel) {
        // Endpoint to register a new parcel
        return ResponseEntity.ok().body(parcelService.registerParcel(parcel));
    }

    @PostMapping("/pickup/{id}")
    public ResponseEntity<?> pickUpParcel(@PathVariable Long id) {
        // Endpoint to mark a parcel as picked up
        return ResponseEntity.ok().body(parcelService.pickUpParcel(id));
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<?> listParcelsForGuest(@PathVariable Long guestId) {
        // Endpoint to list all parcels for a specific guest
        return ResponseEntity.ok().body(parcelService.findAllParcelsForGuest(guestId));
    }
}
