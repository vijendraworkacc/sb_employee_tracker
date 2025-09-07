package com.javadevta.employeetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"addresses", "skills", "manager", "project", "salaryBank"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@Entity
@Table(
        name = "tbl_employee",
        indexes = {
                @Index(name = "idx_email", columnList = "email", unique = true),
                @Index(name = "idx_phone_number", columnList = "phoneNumber", unique = true),
                @Index(name = "idx_salary_bank", columnList = "salary_bank_fk", unique = true),
                @Index(name = "idx_manager", columnList = "manager_fk"),
                @Index(name = "idx_project", columnList = "project_fk")
        }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String firstname;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String lastname;

    @Email
    @NotBlank
    @Size(max = 180)
    @Column(nullable = false, length = 180, unique = true)
    private String email;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20, unique = true)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "salary_bank_fk", nullable = false, unique = true)
    private SalaryBank salaryBank;

    @Builder.Default
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_fk")
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_fk")
    private Project project;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "map_emp_skill",
            joinColumns = @JoinColumn(name = "employee_fk"),
            inverseJoinColumns = @JoinColumn(name = "skill_fk")
    )
    private List<Skill> skills = new ArrayList<>();

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ===== Convenience methods to keep both sides in sync =====
    public void addAddress(Address address) {
        addresses.add(address);
        address.setEmployee(this);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setEmployee(null);
    }

    // Normalize email before saving
    @PrePersist
    @PreUpdate
    private void normalize() {
        if (email != null) {
            email = email.trim().toLowerCase();
        }
    }
}