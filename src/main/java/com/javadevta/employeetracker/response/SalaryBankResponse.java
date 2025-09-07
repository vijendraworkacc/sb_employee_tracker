package com.javadevta.employeetracker.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SalaryBankResponse {
    private String accountNumber;
    private String ifscCode;
}
