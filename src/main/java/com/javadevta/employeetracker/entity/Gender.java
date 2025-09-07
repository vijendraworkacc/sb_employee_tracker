package com.javadevta.employeetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Gender {
    MALE("MALE"), FEMALE("FEMALE"), OTHER("OTHER");
    private final String gender;
}
