package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MR.k0F31n
 */
public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    List<ViewStats> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end, List<String> uris);
}
