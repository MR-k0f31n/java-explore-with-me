package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.dto.enums.RequestStatus;
import ru.practicum.model.Request;

import java.util.List;
import java.util.Optional;

/**
 * @author MR.k0F31N
 */

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByEventId(Long eventId);

    Optional<Request> findByEventIdAndId(Long eventId, Long id);

    int countByEventIdAndStatus(Long eventId, RequestStatus status);

    Boolean existsByEventIdAndRequesterId(Long eventId, Long userId);

    Optional<Request> findByIdAndRequesterId(Long id, Long requesterId);

    List<Request> findAllByRequesterId(Long userId);
}
