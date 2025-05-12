package com.model.guesthousebooking.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "username", name = "idx_username"),
        @Index(columnList = "email", name = "idx_email"),
        @Index(columnList = "created_at", name = "idx_created_at")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true) // Username must be unique
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @Column(nullable = false, unique = true) // Email must be unique
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false) // Password cannot be null
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @Column(nullable = false) // Role of the user (e.g., USER, ADMIN, SUPERADMIN)
    private String role;

    @Column(nullable = false) // Status of the user (e.g., PENDING, APPROVED, REJECTED)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false) // Timestamp for creation
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at") // Timestamp for updates
    private LocalDateTime updatedAt;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private Set<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    @PrePersist // Ensures createdAt is set before persisting to DB
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate // Ensures updatedAt is set before updating the record
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}