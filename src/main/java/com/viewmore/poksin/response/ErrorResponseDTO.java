package com.viewmore.poksin.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String code;
    private String message;

}
