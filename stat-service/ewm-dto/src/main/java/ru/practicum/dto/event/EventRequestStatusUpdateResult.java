package ru.practicum.dto.event;

import lombok.*;
import ru.practicum.dto.input.ParticipationRequestDto;

import java.util.List;

/**
 * @author MR.k0F31n
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
