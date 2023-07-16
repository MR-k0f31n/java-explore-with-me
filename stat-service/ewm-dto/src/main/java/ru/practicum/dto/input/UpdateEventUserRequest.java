package ru.practicum.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.dto.enums.EventStatus;
import ru.practicum.dto.location.Location;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author MR.k0F31n
 */
@Getter
@AllArgsConstructor
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000, message = "update annotation. length text min = 20 max = 2000")
    private String annotation;
    @Positive
    private Long category;
    @Size(min = 20, max = 7000, message = "update description. length text min = 20 max = 7000")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventStatus stateAction;
    @Size(min = 3, max = 120, message = "update title. length text min = 3 max = 120")
    private String title;
}
