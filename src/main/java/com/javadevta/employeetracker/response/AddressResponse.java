package com.javadevta.employeetracker.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AddressResponse {
    private String completeAddress;
    private String zipcode;
    private String city;
    private String state;
    private String country;
    private String type;
}
