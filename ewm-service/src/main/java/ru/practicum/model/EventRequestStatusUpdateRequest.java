package ru.practicum.model;

import lombok.*;
import ru.practicum.enums.RequestStatus;

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
