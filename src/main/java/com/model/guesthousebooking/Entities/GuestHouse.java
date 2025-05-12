package com.model.guesthousebooking.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "guesthouses")
public class GuestHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column
    private String description;

    @OneToMany(mappedBy = "guestHouse", cascade = CascadeType.ALL)
    private List<Room> rooms;
}