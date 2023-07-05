package ru.practicum.dto;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author MR.k0F31N
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    private Long event;
    private LocationDto created;
    private Long requester;
    private String status;
}
