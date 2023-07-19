package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.service.CommentService;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author MR.k0F31N
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("event/{eventId}/comments")
public class CommentControllerPublic {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getAllCommentByEvent(@PathVariable(value = "eventId") @Min(0) Long eventId,
                                                 @RequestParam(value = "from", required = false, defaultValue = "0") @Min(0) Integer from,
                                                 @RequestParam(value = "size", required = false, defaultValue = "10") @Min(1) Integer size) {
        log.trace("Endpoint request: GET /users/{userId}/comments");
        log.debug("Param: event id = '{}', from = '{}', size = '{}'", eventId, from, size);
        final Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return commentService.getAllCommentsFromEvent(eventId, pageable);
    }
}