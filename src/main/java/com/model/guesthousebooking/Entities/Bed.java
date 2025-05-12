package com.model.guesthousebooking.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "beds")
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int bedNumber;

    @Column(nullable = false)
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}