package ru.practicum.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatRepository;
import ru.practicum.service.StatService;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Mapper.toObject;
import static ru.practicum.util.Mapper.viewToDto;

/**
 * @author MR.k0F31n
 */
@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class StatServiceImpl implements StatService {
    private final StatRepository repository;

    @Transactional
    @Override
    public void save(EndpointHitDto input) {
        log.info("Hit input: {}", input);
        EndpointHit object = toObject(input);
        log.info("Save object hit in repository: {}", object);
        repository.save(object);
    }

    @Override
    public List<ViewStatsDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (end.isBefore(start)) {
            log.info("Error detected, start time {}, end time {}", start, end);
            throw new InvalidParameterException("End time no be after start time");
        }
        log.info("Get statistic from {} to {}, for URI's {} ,IP unique: {}", start, end, uris, unique);
        if (unique) {
            return viewToDto(repository.findAllByUrisFromUniqueIp(start, end, uris));
        }
        return viewToDto(repository.findAllByUris(start,end,uris));
    }
}
