package com.javadevta.employeetracker.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProjectResponse {
    private String name;
    private String client;
}
