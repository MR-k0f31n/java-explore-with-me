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
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
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
    public List<ViewStatsDto> getStatistics(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startDate = parseTime(start);
        LocalDateTime endDate = parseTime(end);
        if (endDate.isBefore(startDate)) {
            log.info("Error detected, start time {}, end time {}", start, end);
            throw new InvalidParameterException("End time no be after start time");
        }
        log.info("Get statistic from {} to {}, for URI's {} ,IP unique: {}", start, end, uris, unique);
        if (uris != null && uris.size() > 0) {
            return unique ?
                    viewToDto(repository.getAllByUrisAndUniqueIp(startDate, endDate, uris)) :
                    viewToDto(repository.getAllByUris(startDate, endDate, uris));
        } else {
            return unique ?
                    viewToDto(repository.getAllByUniqueIp(startDate, endDate)) :
                    viewToDto(repository.getAll(startDate, endDate));
        }
    }

    private LocalDateTime parseTime(String date) {
        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeException exception) {
            throw new InvalidParameterException("Incorrect format date: " + date);
        }
    }
}
