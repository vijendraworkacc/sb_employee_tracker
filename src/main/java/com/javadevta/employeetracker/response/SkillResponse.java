package com.javadevta.employeetracker.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SkillResponse {
    private String name;
    private Byte ratingOutOf5;
}