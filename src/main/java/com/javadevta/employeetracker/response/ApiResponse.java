package com.javadevta.employeetracker.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, Object> meta; // optional metadata (e.g., pagination)

    // ---- helpers ----
    public static <T> ApiResponse<T> success(String message, T data, String path) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> successWithMeta(String message, T data, String path, Map<String, Object> meta) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .path(path)
                .timestamp(LocalDateTime.now())
                .meta(meta)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, String path) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
