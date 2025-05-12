package com.model.guesthousebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.model.guesthousebooking") // Ensure proper package scanning
public class GuestHouseBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuestHouseBookingApplication.class, args);
    }
}