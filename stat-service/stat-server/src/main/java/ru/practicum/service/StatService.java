package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MR.k0F31n
 */
public interface StatService {
    void save (EndpointHitDto input);

    List<ViewStatsDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
