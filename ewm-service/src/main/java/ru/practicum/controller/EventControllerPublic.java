package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.service.EventService;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/events")
public class EventControllerPublic {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getAllEvents(@RequestParam(value = "text", defaultValue = "") String text,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(defaultValue = "false") Boolean paid,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                            @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
                                            @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size,
                                            HttpServletRequest request) {
        log.trace("Public endpoint request: GET /events");
        log.debug("Param: search by = '{}', id categories = '{}', paid = {}, start range = '{}', end range = '{}', " +
                        "only available = {}, sort by = '{}', pageable from = '{}', pageable size = '{}'", text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        final Pageable pageable = PageRequest.of(from / size, size);
        return eventService.getAllEventFromPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, pageable, request);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable(value = "eventId") @Min(1) Long eventId,
                                     HttpServletRequest request) {
        log.trace("Public endpoint request: GET /events/{eventId}");
        log.debug("Param: event id ='{}'", eventId);
        return eventService.getEventById(eventId, request);
    }
}
