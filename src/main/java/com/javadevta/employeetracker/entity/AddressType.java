package com.javadevta.employeetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum AddressType {
    TEMPORARY("TEMPORARY"), PERMANENT("PERMANENT");
    private final String type;
}
