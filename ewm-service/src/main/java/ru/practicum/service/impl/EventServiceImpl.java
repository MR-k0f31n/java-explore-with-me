
package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatClient;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.dto.event.*;
import ru.practicum.dto.enums.EventStatus;
import ru.practicum.dto.input.NewEventDto;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.dto.input.UpdateEventAdminRequest;
import ru.practicum.dto.input.UpdateEventUserRequest;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.TimeValidationException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.*;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final StatClient statClient;
    private final RequestRepository requestRepository;

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, Pageable pageable) {
        isExistsUser(userId);
        final List<Event> result = eventRepository.findAllByInitiatorId(userId, pageable);
        log.debug("Finded '{}' events by user id = '{}', sort by = '{}'", result.size(), userId, pageable.getSort());
        return result.stream().map(EventMapper::toShortDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto createNewEvent(Long userId, NewEventDto eventDto) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id = '" + userId + "' not found!"));
        log.debug("User received");
        isBeforeTwoHours(eventDto.getEventDate());
        final Category category = getCategoryById(eventDto.getCategory());
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
        final Event event = getEvenByInitiatorAndEventId(userId, eventId);
        log.debug("Event received [{}]", event);
        return toFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto updateEventByUsersIdAndEventIdFromUser(Long userId, Long eventId, UpdateEventUserRequest update) {
        isExistsUser(userId);
        int sizeUpdate = 0;
        final Event oldEvent = getEvenByInitiatorAndEventId(userId, eventId);
        if (update.getEventDate() != null) {
            final LocalDateTime newDate = update.getEventDate();
            isBeforeTwoHours(newDate);
            oldEvent.setEventDate(newDate);
            sizeUpdate++;
            log.debug("Event date update '{}'", newDate);
        }
        if (!oldEvent.getInitiator().getId().equals(userId))
            throw new ValidationException("Only owner can update event");
        if (oldEvent.getEventStatus().equals(EventStatus.PUBLISHED))
            throw new ValidationException("Only reject or cancelled can update event");
        if (update.getAnnotation() != null && !update.getAnnotation().isBlank()) {
            oldEvent.setAnnotation(update.getAnnotation());
            sizeUpdate++;
            log.debug("Annotation updated '{}'", oldEvent.getAnnotation());
        }
        if (update.getCategory() != null) {
            final Category category = getCategoryById(update.getCategory());
            log.debug("Category received");
            oldEvent.setCategory(category);
            sizeUpdate++;
            log.debug("Category update [{}]", category);
        }
        if (update.getDescription() != null) {
            oldEvent.setDescription(update.getDescription());
            sizeUpdate++;
            log.debug("Description update '{}'", oldEvent.getDescription());
        }
        if (update.getLocation() != null) {
            oldEvent.setLocation(update.getLocation());
            sizeUpdate++;
            log.debug("Location update [{}]", oldEvent.getLocation());
        }
        if (update.getParticipantLimit() != null) {
            oldEvent.setParticipantLimit(update.getParticipantLimit());
            sizeUpdate++;
            log.debug("Participant limit update '{}'", oldEvent.getParticipantLimit());
        }
        if (update.getRequestModeration() != null) {
            oldEvent.setRequestModeration(update.getRequestModeration());
            sizeUpdate++;
            log.debug("Request moderation update '{}'", oldEvent.getRequestModeration());
        }
        if (update.getStateAction() != null) {
            oldEvent.setEventStatus(update.getStateAction());
            sizeUpdate++;
            log.debug("State update '{}'", oldEvent.getEventStatus());
        }
        if (update.getTitle() != null) {
            oldEvent.setTitle(update.getTitle());
            sizeUpdate++;
            log.debug("Title update '{}'", oldEvent.getTitle());
        }
        Event eventAfterUpdate = null;
        if (sizeUpdate > 0) eventAfterUpdate = eventRepository.save(oldEvent);
        log.debug("Event update? if null no new data: [{}]", eventAfterUpdate);
        return eventAfterUpdate != null ? toFullDto(eventAfterUpdate) : null;
    }

    @Override
    public List<ParticipationRequestDto> getAllParticipationRequestsFromEventByOwner(Long userId, Long eventId) {
        isExistsUser(userId);
        isUserInitiatedEvent(userId, eventId);
        final List<Request> requests = requestRepository.findAllByEventId(eventId);
        log.debug("Number of requests found '{}'", requests.size());
        return requests.stream().map(RequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateStatusRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest update) {
        isExistsUser(userId);
        final Event event = getEvenByInitiatorAndEventId(userId, eventId);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0)
            throw new ValidationException("This event does not require confirmation of requests");
        final RequestStatus status = update.getStatus();

        switch (status) {
            case CONFIRMED:
                if (event.getParticipantLimit().equals(event.getConfirmedRequests()))
                    throw new ValidationException("Participant limit is full");
                CaseUpdatedStatus updatedStatusConfirmed = statusHandler(event, CaseUpdatedStatus.builder()
                        .idsFromUpdateStatus(update.getRequestIds()).build(), RequestStatus.CONFIRMED);
                List<Request> confirmedRequests = requestRepository.findAllById(updatedStatusConfirmed.getProcessedIds());
                log.debug("Confirmed requests '{};", confirmedRequests.size());
                List<Request> rejectedRequests = new ArrayList<>();
                if (updatedStatusConfirmed.getIdsFromUpdateStatus().size() != 0) {
                    List<Long> ids = updatedStatusConfirmed.getIdsFromUpdateStatus();
                    int sizeIds = updatedStatusConfirmed.getIdsFromUpdateStatus().size();
                    for (int i = 0; i < sizeIds; i++) {
                        final Request request = getRequestOrThrow(eventId, updatedStatusConfirmed.(i), RequestStatus.PENDING);
                        log.debug("Request received [{}]", request);
                        request.setStatus(RequestStatus.REJECTED);
                        log.debug("Status update '{}'", request.getStatus());
                        requestRepository.save(request);
                        log.debug("Request saved!");
                    }
                    rejectedRequests = requestRepository.findAllById(ids);
                    log.debug("Reject requests '{}'", rejectedRequests.size());
                }
                log.debug("Reject requests '{}'", rejectedRequests.size());
                return EventRequestStatusUpdateResult.builder()
                        .confirmedRequests(confirmedRequests.stream().map(RequestMapper::toDto).collect(Collectors.toList()))
                        .rejectedRequests(rejectedRequests.stream().map(RequestMapper::toDto).collect(Collectors.toList()))
                        .build();
            case REJECTED:
                if (event.getParticipantLimit().equals(event.getConfirmedRequests()))
                    throw new ValidationException("Participant limit is full");
                CaseUpdatedStatus updatedStatusReject = statusHandler(event, CaseUpdatedStatus.builder()
                        .idsFromUpdateStatus(update.getRequestIds()).build(), RequestStatus.REJECTED);
                return null;
        }
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
    public List<EventShortDto> getAllEventFromPublic(String search, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public EventFullDto getEventById(Long eventId, HttpServletRequest request) {
        return null;
    }

    private CaseUpdatedStatus statusHandler (Event event, CaseUpdatedStatus caseUpdatedStatus, RequestStatus status) {
        Long eventId = event.getId();
        final List<Long> ids = caseUpdatedStatus.getIdsFromUpdateStatus();
        final int idsSize = caseUpdatedStatus.getIdsFromUpdateStatus().size();
        final List<Long> processedIds = new ArrayList<>();
        int freeRequest = event.getParticipantLimit() - event.getConfirmedRequests();
        for (int i = 0; i < idsSize; i++) {
            final Request request = getRequestOrThrow(eventId, ids.get(i), RequestStatus.PENDING);
            if (freeRequest == 0) {
                break;
            }
            log.debug("Request received [{}]", request);
            request.setStatus(status);
            log.debug("Status update '{}'", request.getStatus());
            requestRepository.save(request);
            log.debug("Request saved!");
            Long confirmedId = request.getId();
            processedIds.add(confirmedId);
            ids.remove(i);
            freeRequest--;
        }
        Integer confirmedRequestUpdate = event.getConfirmedRequests() + processedIds.size();
        event.setConfirmedRequests(confirmedRequestUpdate);
        eventRepository.save(event);
        caseUpdatedStatus.setIdsFromUpdateStatus(ids);
        caseUpdatedStatus.setProcessedIds(processedIds);
        return caseUpdatedStatus;
    }

    private void isExistsUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.debug("User with id = '{}' not found", userId);
            throw new NotFoundException("User with id = '" + userId + "' not found!");
        }
        log.debug("Check user exist - ok");
    }

    private void isExistsEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            log.debug("Event with id = '{}' not found", eventId);
            throw new NotFoundException("Event with id = '" + eventId + "' not found");
        }
        log.debug("Check event exist - ok");
    }

    private void isBeforeTwoHours(LocalDateTime eventDateStarted) {
        final LocalDateTime twoHoursLater = LocalDateTime.now().plusHours(2);
        if (eventDateStarted.isBefore(twoHoursLater)) {
            log.debug("The event will start in less than 2 hours. Date start '{}'", eventDateStarted);
            throw new TimeValidationException("Time start event cannot be after two hours started event");
        }
        log.debug("Time started event is check - ok");
    }

    private Event getEvenByInitiatorAndEventId(Long userId, Long eventId) {
        return eventRepository.findByInitiatorIdAndId(userId, eventId).orElseThrow(
                () -> new NotFoundException("Event with id = '" + eventId + "' and/or initiator with id = '" + userId + "' not found"));
    }

    private void isCategoryExists(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new NotFoundException("Category with id = '" + catId + "' not found");
        }
        log.debug("Check user exist - ok");
    }

    private Category getCategoryById(Long caId) {
        return categoryRepository.findById(caId).orElseThrow(
                () -> new NotFoundException("Category with id = '" + caId + "' not found"));
    }

    private void isUserInitiatedEvent(Long userId, Long eventId) {
        if (!eventRepository.existsByInitiatorIdAndId(userId, eventId)) {
            throw new ValidationException("User did not create this event");
        }
        log.debug("Initiator check - ok");
    }

    private Request getRequestOrThrow(Long eventId, Long reqId, RequestStatus status) {
        return requestRepository.findByEventIdAndIdAndStatus(eventId, reqId, status).orElseThrow(
                () -> new NotFoundException("Request with id = '" + reqId + "' not found or with event id = '" + eventId + "' " +
                        "or not found request on status '" + status + "'"));
    }
}
