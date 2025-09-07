package com.javadevta.employeetracker.mapper;

import com.javadevta.employeetracker.entity.*;
import com.javadevta.employeetracker.response.*;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse toResponse(Employee employee) {
        if (employee == null) return null;

        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .gender(employee.getGender().name())
                .salaryBank(toSalaryBankResponse(employee.getSalaryBank()))
                .addresses(employee.getAddresses().stream()
                        .map(this::toAddressResponse)
                        .toList())
                .project(toProjectResponse(employee.getProject()))
                .skills(employee.getSkills().stream()
                        .map(this::toSkillResponse)
                        .toList())
                .build();
    }

    private SalaryBankResponse toSalaryBankResponse(SalaryBank sb) {
        if (sb == null) return null;
        return new SalaryBankResponse(sb.getAccountNumber(), sb.getIfscCode());
    }

    private AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .completeAddress(address.getCompleteAddress())
                .zipcode(address.getZipcode())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .type(address.getType().name())
                .build();
    }

    private ProjectResponse toProjectResponse(Project p) {
        if (p == null) return null;
        return new ProjectResponse(p.getName(), p.getClient());
    }

    private SkillResponse toSkillResponse(Skill s) {
        return new SkillResponse(s.getName(), s.getRatingOutOf5());
    }
}
