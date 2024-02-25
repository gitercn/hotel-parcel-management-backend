package com.hotel.parceltrackingservice.controller;

import com.hotel.parceltrackingservice.model.Guest;
import com.hotel.parceltrackingservice.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("/checked-in")
    public ResponseEntity<?> listCheckedInGuests() {
        // Endpoint to list all checked-in guests
        return ResponseEntity.ok().body(guestService.findAllCheckedInGuests());
    }

    @PostMapping("/check-in")
    public ResponseEntity<?> checkInGuest(@RequestBody Guest guest) {
        // Endpoint to check in a guest
        return ResponseEntity.ok().body(guestService.checkInGuest(guest));
    }

    @PostMapping("/check-out/{id}")
    public ResponseEntity<?> checkOutGuest(@PathVariable Long id) {
        // Endpoint to check out a guest
        return ResponseEntity.ok().body(guestService.checkOutGuest(id));
    }
}
