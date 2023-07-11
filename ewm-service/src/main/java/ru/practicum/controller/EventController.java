package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.*;
import ru.practicum.dto.input.NewEventDto;
import ru.practicum.dto.input.ParticipationRequestDto;
import ru.practicum.dto.input.UpdateEventUserRequest;
import ru.practicum.exception.IncorrectParametersException;
import ru.practicum.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MR.k0F31n
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventService eventService;

    @GetMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllEventsByUserId(@PathVariable(value = "userId") @Min(1) Long userId,
                                                    @RequestParam(value = "from", required = false, defaultValue = "0") @Min(0) Integer from,
                                                    @RequestParam(value = "size", required = false, defaultValue = "10") @Min(1) Integer size) {
        log.trace("Endpoint request: GET /users/{userId}/events");
        log.debug("Param: user id = '{}', from = '{}', size = '{}'", userId, from, size);
        final Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        return eventService.getEventsByUserId(userId, pageable);
    }

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createNewEvent(@PathVariable(value = "userId") @Min(1) Long userId,
                                       @Valid @RequestBody NewEventDto input) {
        log.trace("Endpoint request: POST /users/{userId}/events");
        log.debug("Param: input body '{}'", input);
        return eventService.createNewEvent(userId, input);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getFullEventByOwner(@PathVariable(value = "userId") @Min(1) Long userId,
                                            @PathVariable(value = "eventId") @Min(1) Long eventId) {
        log.trace("Endpoint request: GET /users/{userId}/events/{eventId}");
        log.debug("Param: user id = '{}', event id = '{}'", userId, eventId);
        return eventService.getEventByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventByOwner(@PathVariable(value = "userId") @Min(1) Long userId,
                                           @PathVariable(value = "eventId") @Min(1) Long eventId,
                                           @RequestBody UpdateEventUserRequest inputUpdate) {
        log.trace("Endpoint request: PATCH /users/{userId}/events/{eventId}");
        log.debug("Param: user id = '{}', event id = '{}', request body = '{}'", userId, eventId, inputUpdate);
        return eventService.updateEventByUsersIdAndEventIdFromUser(userId, eventId, inputUpdate);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getAllRequestByEventFromOwner(@PathVariable(value = "userId") @Min(1) Long userId,
                                                                       @PathVariable(value = "eventId") @Min(1) Long eventId) {
        log.trace("Endpoint request: GET /users/{userId}/events/{eventId}/requests");
        log.debug("Param: user id = '{}', event id = '{}'", userId, eventId);
        return eventService.getAllParticipationRequestsFromEventByOwner(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateStatusRequestFromOwner(@PathVariable(value = "userId") @Min(1) Long userId,
                                                                       @PathVariable(value = "eventId") @Min(1) Long eventId,
                                                                       @RequestBody EventRequestStatusUpdateRequest inputUpdate) {
        log.trace("Endpoint request: PATCH /users/{userId}/events/{eventId}/requests");
        log.debug("Param: user id = '{}', event id = '{}', object update = '{}'", userId, eventId, inputUpdate);
        return eventService.updateStatusRequests(userId, eventId, inputUpdate);
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> searchEvents(@RequestParam(required = false) List<Long> users,
                                           @RequestParam(required = false) List<String> states,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                           @RequestParam(value = "from", required = false, defaultValue = "0") @Min(0) Integer from,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") @Min(1) Integer size) {
        log.trace("Endpoint request: GET /admin/events");
        log.debug("Param: search id user = '{}', search state events = '{}', search id categories = '{}', range start = '{}', " +
                "range end = '{}', pageable from = '{}', pageable size = '{}'", users, states, categories, rangeStart, rangeEnd, from, size);
        if (rangeStart.isAfter(rangeEnd)) {
            throw new IncorrectParametersException("Range start before range end");
        }
        final Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        return eventService.getAllEventForParamFromAdmin(users, states, categories, rangeStart, rangeEnd, pageable);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable(value = "eventId") @Min(1) Long eventId,
                                           @RequestBody @Valid UpdateEventAdminRequest inputUpdate) {
        log.trace("Endpoint request: PATCH /admin/events/{eventId}");
        log.debug("Param: event id = '{}', object update = '{}'", eventId, inputUpdate);
        return eventService.updateEventByEventIdFromAdmin(eventId, inputUpdate);
    }

    @GetMapping("/events")
    public List<EventShortDto> getAllEvents(@RequestParam(value = "text", required = false) String search,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(defaultValue = "false") Boolean paid,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(value = "sort", defaultValue = "event_date") String sortBy,
                                            @RequestParam(value = "from", required = false, defaultValue = "0") @Min(0) Integer from,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") @Min(1) Integer size) {
        log.trace("Public endpoint request: GET /events");
        log.debug("Param: search by = '{}', id categories = '{}', paid = {}, start range = '{}', end range = '{}', " +
                        "only available = {}, sort by = '{}', pageable from = '{}', pageable size = '{}'", search, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sortBy, from, size);
        final Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, sortBy));
        return eventService.getAllEventFromPublic(search, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getEventById(@PathVariable(value = "eventId") @Min(1) Long eventId) {
        log.trace("Public endpoint request: GET /events/{eventId}");
        log.debug("Param: event id ='{}'", eventId);
        return eventService.getEventById(eventId);
    }
}
