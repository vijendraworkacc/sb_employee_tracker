package com.javadevta.employeetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
        name = "tbl_skill",
        indexes = {
                @Index(name = "idx_name", columnList = "name", unique = true)
        }
)
public class Skill {
    @GeneratedValue
    @Id
    private Integer id;
    @NotBlank
    private String name;
    private Byte ratingOutOf5;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "skills")
    private List<Employee> employees = new ArrayList<>();
}
