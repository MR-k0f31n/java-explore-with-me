package ru.practicum.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.event.*;
import ru.practicum.dto.input.NewEventDto;
import ru.practicum.dto.input.ParticipationRequestDto;
import ru.practicum.dto.input.UpdateEventUserRequest;
import ru.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MR.k0F31N
 */
@Service
public class EventServiceImpl implements EventService {
    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public EventFullDto createNewEvent(Long userId, NewEventDto eventDto) {
        return null;
    }

    @Override
    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateEventByUsersIdAndEventIdFromUser(Long userId, Long eventId, UpdateEventUserRequest update) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getAllParticipationRequestsFromEventByOwner(Long userId, Long EventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest update) {
        return null;
    }

    @Override
    public List<EventFullDto> getAllEventForParamFromAdmin(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable) {
        return null;
    }

    @Override
    public EventFullDto updateEventByEventIdFromAdmin(Long eventId, UpdateEventAdminRequest update) {
        return null;
    }

    @Override
    public List<EventShortDto> getAllEventFromPublic(String search, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable) {
        return null;
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        return null;
    }
}
