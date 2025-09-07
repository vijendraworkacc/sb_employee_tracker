package com.javadevta.employeetracker.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EmployeeResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String gender;

    private SalaryBankResponse salaryBank;
    private List<AddressResponse> addresses;
    private ProjectResponse project;
    private List<SkillResponse> skills;
}
