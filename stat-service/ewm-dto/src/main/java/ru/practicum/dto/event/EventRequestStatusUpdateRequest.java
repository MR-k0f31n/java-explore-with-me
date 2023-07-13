package ru.practicum.dto.event;

import lombok.*;
import ru.practicum.dto.enums.RequestStatus;

import java.util.List;

/**
 * @author MR.k0F31n
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private RequestStatus status;
}
