package ru.practicum.dto.input;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.dto.location.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author MR.k0F31n
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {
    @NotBlank(message = "Annotation cannot be blank")
    @Length(max = 2000, min = 20, message = "Annotation cannot be min = 20 max = 200")
    private String annotation;
    /**
     * ID category
     */
    @NotNull(message = "Id category cannot be null")
    @Positive(message = "Id category can be only positive")
    private Long category;
    @Length(max = 7000, min = 20, message = "Description cannot be length min 20 max 7000")
    private String description;
    @NotBlank(message = "Event Date cannot be blank")
    private String eventDate;
    @NotNull(message = "Location cannot be null")
    private Location location;
    @NotNull(message = "Paid cannot be null")
    private Boolean paid;
    @PositiveOrZero(message = "Participant Limit can be only zero(unlimited) or positive")
    private Integer participantLimit;
    @NotNull(message = "Request Moderation cannot be null")
    private Boolean requestModeration;
    @NotNull(message = "Title cannot be null")
    @Length(min = 3, max = 120, message = "Title cannot be length min 3 max 120")
    private String title;
}
