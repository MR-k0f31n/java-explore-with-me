package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.event.*;
import ru.practicum.dto.input.NewEventDto;
import ru.practicum.dto.input.ParticipationRequestDto;
import ru.practicum.dto.input.UpdateEventUserRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MR.k0F31n
 */
public interface EventService {
    List<EventShortDto> getEventsByUserId(Long userId, Pageable pageable);

    EventFullDto createNewEvent(Long userId, NewEventDto eventDto);

    EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId);

    EventFullDto updateEventByUsersIdAndEventIdFromUser(Long userId, Long eventId, UpdateEventUserRequest update);

    List<ParticipationRequestDto> getAllParticipationRequestsFromEventByOwner(Long userId, Long EventId);

    EventRequestStatusUpdateResult updateStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest update);

    List<EventFullDto> getAllEventForParamFromAdmin(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart,
                                                    LocalDateTime rangeEnd, Pageable pageable);

    EventFullDto updateEventByEventIdFromAdmin(Long eventId, UpdateEventAdminRequest update);

    List<EventShortDto> getAllEventFromPublic(String search, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable);

    EventFullDto getEventById(Long eventId);
}
