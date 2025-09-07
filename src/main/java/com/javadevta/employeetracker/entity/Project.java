package com.javadevta.employeetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "employees")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@Entity
@Table(
        name = "tbl_project",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_project_name_client", columnNames = {"name", "client"})
        },
        indexes = {
                @Index(name = "idx_project_name", columnList = "name"),
                @Index(name = "idx_project_client", columnList = "client")
        }
)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(updatable = false, nullable = false)
    private Long id; // use Long for consistency

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String client;

    @NotNull
    @Column(nullable = false)
    private LocalDate mapStartDate;

    // If your domain allows open-ended projects, keep this nullable.
    @Column
    private LocalDate mapEndDate;

    @Builder.Default
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ===== Helpers to keep both sides in sync =====
    public void addEmployee(Employee e) {
        if (e == null) return;
        employees.add(e);
        e.setProject(this);
    }

    public void removeEmployee(Employee e) {
        if (e == null) return;
        employees.remove(e);
        if (e.getProject() == this) e.setProject(null);
    }

    // ===== Validation & normalization =====
    @AssertTrue(message = "mapEndDate must be on or after mapStartDate")
    @Transient
    public boolean isDateRangeValid() {
        return mapStartDate == null
                || mapEndDate == null
                || !mapEndDate.isBefore(mapStartDate);
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (name != null)   name = name.trim();
        if (client != null) client = client.trim();
    }
}