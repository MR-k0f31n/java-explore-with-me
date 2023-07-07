package ru.practicum.dto.input;

import lombok.*;
import ru.practicum.dto.location.Location;

/**
 * @author MR.k0F31n
 */
@Getter
@AllArgsConstructor
public class UpdateEventUserRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String stateAction;
    private String title;
}
