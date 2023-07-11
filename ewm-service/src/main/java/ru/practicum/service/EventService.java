package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.input.NewEventDto;
import ru.practicum.dto.input.ParticipationRequestDto;
import ru.practicum.dto.input.UpdateEventUserRequest;

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

    // изменение статуса заявки на участие нет реквестов!

    List<EventFullDto> getAllEventForParamFromAdmin(Long[] users, String[] states, Long[] categories, String rangeStart, String rangeEnd, Pageable pageable);

    EventFullDto getEventByIdFromAdmin(Long eventId);

    List<EventShortDto> getAllEventFromPublic(String search, String[] categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, Pageable pageable);
}
