package ru.practicum.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MR.k0F31N
 */

@Getter
@Builder
public class ApiError {

    private final List<Error> errors;
    private final String message;
    private final String reason;
    private final String status;
    private final LocalDateTime timestamp = LocalDateTime.now();

}
