package ru.practicum.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.dto.location.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author MR.k0F31n
 */
@Getter
@AllArgsConstructor
public class UpdateEventUserRequest {
    private String annotation;
    private Long category;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String stateAction;
    private String title;
}
