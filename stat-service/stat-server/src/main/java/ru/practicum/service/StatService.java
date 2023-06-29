package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MR.k0F31n
 */
public interface StatService {
    /**
     * Save info from statistic
     * @param input
     */
    void save (EndpointHitDto input);

    /**
     * Get collection statistics from date start to end date, for uris list and unique or not IP
     * @param start date start report
     * @param end date end report
     * @param uris list uri's
     * @param unique unique IP true or false
     * @return List short stat
     */
    List<ViewStatsDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
