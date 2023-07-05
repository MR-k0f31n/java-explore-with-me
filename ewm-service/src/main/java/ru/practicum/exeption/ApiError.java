package ru.practicum.exeption;

import lombok.*;

import java.util.List;

/**
 * @author MR.k0F31N
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private List<Error> errors;
    private String message;
    private String reason;
    private String status;
    private String timestamp;

}
