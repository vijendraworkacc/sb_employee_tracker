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
        name = "tbl_salary_bank",
        indexes = {
                @Index(name = "idx_account_number", columnList = "accountNumber", unique = true),
                @Index(name = "idx_ifsc_code", columnList = "ifscCode")
        }
)
public class SalaryBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(updatable = false, nullable = false)
    private Long id; // switched from accountNumber to Long for consistency

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20, unique = true)
    private String accountNumber;   // Business unique field

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String ifscCode;

    // Inverse side of the OneToOne with Employee
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "salaryBank", optional = false)
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
        if (accountNumber != null) accountNumber = accountNumber.trim();
        if (ifscCode != null) ifscCode = ifscCode.trim().toUpperCase();
    }
}