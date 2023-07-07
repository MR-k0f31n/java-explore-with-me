package ru.practicum.dto.event;

import lombok.*;

/**
 * @author MR.k0F31N
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventFullDto {
    private Long id;
    private String annotation;
    private String category;
    private Integer confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private String initiator;
    private String location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private String state;
    private String title;
    private Integer views;
}
