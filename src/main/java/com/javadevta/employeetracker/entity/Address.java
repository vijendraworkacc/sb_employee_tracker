package com.javadevta.employeetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "employee")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@Entity
@Table(
        name = "tbl_address",
        indexes = {
                @Index(name = "idx_zipcode", columnList = "zipcode"),
                @Index(name = "idx_employee_fk", columnList = "employee_fk")
        }
)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(updatable = false, nullable = false)
    private Long id;  // Changed to Long for consistency across entities

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String completeAddress;

    @NotBlank
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    private String zipcode;

    @Size(max = 50)
    @Column(length = 50)
    private String landmark; // nullable by default

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String city;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String state;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String country;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AddressType type;

    // Correct owning-side mapping for the FK on tbl_address.employee_fk
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_fk", nullable = false)
    private Employee employee;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ===== Normalize before save =====
    @PrePersist
    @PreUpdate
    private void normalize() {
        if (city != null) city = city.trim();
        if (state != null) state = state.trim();
        if (country != null) country = country.trim();
        if (zipcode != null) zipcode = zipcode.trim();
        if (landmark != null) landmark = landmark.trim();
    }
}