package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.enums.EventStatus;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.exception.IncorrectParametersException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidatedException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.RequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.RequestMapper.toDto;

/**
 * @author MR.k0F31N
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public ParticipationRequestDto createdNewRequest(Long userId, Long eventId) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id = '" + userId + "' not found"));
        final Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Event with id '" + eventId + "' not found"));
        final LocalDateTime createdOn = LocalDateTime.now();
        validationNewRequest(event, userId, eventId);
        final Request request = new Request();
        request.setCreated(createdOn);
        request.setRequester(user);
        request.setEvent(event);
        if (event.getRequestModeration()) {
            request.setStatus(RequestStatus.PENDING);
        } else request.setStatus(RequestStatus.CONFIRMED);
        final Request requestAfterSave = requestRepository.save(request);
        log.debug("Request after canceled = [{}]", requestAfterSave);
        int countRequestConfirmed = requestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
        if (event.getConfirmedRequests() != countRequestConfirmed) {
            event.setConfirmedRequests(countRequestConfirmed);
            eventRepository.save(event);
            log.debug("Update confirmed request");
        }
        if (event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
        }
        return toDto(request);
    }

    private void validationNewRequest(Event event, Long userId, Long eventId) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new ValidatedException("Owner cannot be a member");
        }
        if (event.getParticipantLimit() > 0 && event.getParticipantLimit() <= requestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED)) {
            throw new ValidatedException("Limit seat is full");
        }
        if (!event.getEventStatus().equals(EventStatus.PUBLISHED)) {
            throw new ValidatedException("Event not published");
        }
        if (requestRepository.existsByEventIdAndRequesterId(eventId, userId)) {
            throw new ValidatedException("Cannot add duplicate request");
        }
    }

    @Override
    public List<ParticipationRequestDto> getAllRequests(Long userId) {
        isUserExists(userId);
        List<Request> result = requestRepository.findAllByRequesterId(userId);
        log.debug("Found requests number {}", result.size());
        return result.stream().map(RequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        isUserExists(userId);
        final Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(
                () -> new NotFoundException(String
                        .format("Request with id = '%d', with same requester id = '%d' not found", requestId, userId)));
        if (request.getStatus().equals(RequestStatus.CANCELED) || request.getStatus().equals(RequestStatus.REJECTED)) {
            throw new IncorrectParametersException("Request already canceled");
        }
        request.setStatus(RequestStatus.CANCELED);
        final Request requestAfterSave = requestRepository.save(request);
        log.debug("Request after canceled = [{}]", requestAfterSave);
        return toDto(requestAfterSave);
    }

    private void isUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id '%d' not found", userId));
        }
    }
}
