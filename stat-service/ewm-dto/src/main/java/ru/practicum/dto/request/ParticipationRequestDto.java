package ru.practicum.dto.request;

import lombok.*;
import ru.practicum.dto.enums.RequestStatus;

import java.time.LocalDateTime;

/**
 * @author MR.k0F31N
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequestDto {
    private Long id;
    private Long event;
    private LocalDateTime created;
    private Long requester;
    private RequestStatus status;
}
