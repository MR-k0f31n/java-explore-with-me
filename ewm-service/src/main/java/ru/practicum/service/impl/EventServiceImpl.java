package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.event.*;
import ru.practicum.dto.event.enums.EventStatus;
import ru.practicum.dto.input.NewEventDto;
import ru.practicum.dto.input.ParticipationRequestDto;
import ru.practicum.dto.input.UpdateEventUserRequest;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.TimeValidationException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.EventMapper.toFullDto;
import static ru.practicum.mapper.EventMapper.toModel;

/**
 * @author MR.k0F31N
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, Pageable pageable) {
        isExistsUser(userId);
        final List<Event> result = eventRepository.findAllByInitiatorId(userId, pageable);
        log.debug("Finded '{}' events by user id = '{}', sort by = '{}'", result.size(), userId, pageable.getSort());
        return result.stream().map(EventMapper::toShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto createNewEvent(Long userId, NewEventDto eventDto) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id = '" + userId + "' not found!"));
        log.debug("User received");
        final LocalDateTime twoHoursLater = LocalDateTime.now().plusHours(2);
        if (eventDto.getEventDate().isBefore(twoHoursLater))
            throw new TimeValidationException("Time start event cannot be after two hours started event");
        log.debug("Time started event is check - ok");
        final Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(
                () -> new NotFoundException("Category with id = '" + eventDto.getCategory() + "' not found"));
        log.debug("Category received");
        final Event event = toModel(eventDto);
        log.debug("Convert to event. Event after : [{}]", event);
        event.setCategory(category);
        log.debug("Event set category [{}]", category);
        event.setInitiator(user);
        log.debug("Event set initiator: [{}]", user);
        event.setEventStatus(EventStatus.PENDING);
        log.debug("Event set status '{}'", EventStatus.PENDING);
        event.setCreatedDate(LocalDateTime.now());
        log.debug("Event set created date '{}'", event.getCreatedDate());
        event.setConfirmedRequests(0);
        event.setViews(0);
        log.debug("Event set view adn confirmed requests '0'");
        final Event eventSaved = eventRepository.save(event);
        log.debug("Event saved. Event after save - '{}'", eventSaved);
        return toFullDto(eventSaved);
    }

    @Override
    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        isExistsUser(userId);
        log.debug("Check user exist - ok");
        final Event event = eventRepository.findByInitiatorIdAndId(userId, eventId).orElseThrow(
                () -> new NotFoundException("Event with id = '" + eventId + "' and/or initiator with id = '" + userId + "' not found"));
        log.debug("Event received [{}]", event);
        return toFullDto(event);
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

    private void isExistsUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.debug("User with id = '{}' not found", userId);
            throw new NotFoundException("User with id = '" + userId + "' not found!");
        }
    }

    private void isExistsEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            log.debug("Event with id = '{}' not found", eventId);
            throw new NotFoundException("Event with id = '" + eventId + "' not found");
        }
    }
}
