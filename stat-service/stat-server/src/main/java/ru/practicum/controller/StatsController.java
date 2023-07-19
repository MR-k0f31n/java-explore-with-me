package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.statistic.EndpointHitDto;
import ru.practicum.dto.statistic.ViewStatsDto;
import ru.practicum.service.StatService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author MR.k0F31n
 */

@RestController
@AllArgsConstructor
@Slf4j
@Validated
public class StatsController {
    private final StatService service;

    @PostMapping(value = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody EndpointHitDto inputHit) {
        log.info("Hit save : {}", inputHit);
        service.save(inputHit);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStatistics(@NotEmpty @RequestParam(value = "start") String start,
                                            @NotEmpty @RequestParam(value = "end") String end,
                                            @RequestParam(required = false) List<String> uris,
                                            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Get statistic, param - data start: {}, data end: {}, List uris: {}, unique: {}", start, end, uris, unique);
        return service.getStatistics(start, end, uris, unique);
    }
}
