package com.hotel.parceltrackingservice.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "parcels")
@Getter
@Setter
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parcel_number", nullable = false, unique = true)
    private String parcelNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @Column(name = "receptionist_comment")
    private String receptionistComment;

    @Column(name = "is_picked_up", nullable = false)
    private boolean isPickedUp = false;
}
