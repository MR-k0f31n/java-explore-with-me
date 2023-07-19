package ru.practicum.service;

import ru.practicum.dto.request.ParticipationRequestDto;

import java.util.List;

/**
 * @author MR.k0F31N
 */

public interface RequestService {
    ParticipationRequestDto createdNewRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getAllRequests(Long userId);

    ParticipationRequestDto cancel(Long userId, Long requestId);
}
