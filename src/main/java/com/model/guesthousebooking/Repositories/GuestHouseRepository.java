package com.model.guesthousebooking.Repositories;

import com.model.guesthousebooking.Entities.GuestHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestHouseRepository extends JpaRepository<GuestHouse, Long> {
}