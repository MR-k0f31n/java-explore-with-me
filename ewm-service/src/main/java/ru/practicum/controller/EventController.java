package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.model.EventRequestStatusUpdateRequest;
import ru.practicum.model.EventRequestStatusUpdateResult;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.input.NewEventDto;
import ru.practicum.dto.input.ParticipationRequestDto;
import ru.practicum.dto.input.UpdateEventUserRequest;
import ru.practicum.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
        return null;
    }
}
