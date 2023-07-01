package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MR.k0F31n
 */

@RestController
@AllArgsConstructor
@Slf4j
public class StatsController {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final StatService service;

    @PostMapping("/hit")
    public void save(@RequestBody EndpointHitDto inputHit) {
        log.info("Hit save : {}", inputHit);
        service.save(inputHit);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStatistics(@DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                            @RequestParam(value = "start") LocalDateTime start,
                                            @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                            @RequestParam(value = "end") LocalDateTime end,
                                            @RequestParam(required = false) List<String> uris,
                                            @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("Get statistic, param - data start: {}, data end: {}, List uris: {}, unique: {}", start, end, uris, unique);
        return service.getStatistics(start, end, uris, unique);
    }
}
