package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.service.CompilationService;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author MR.k0F31N
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/compilations")
public class CompilationControllerPublic {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(value = "pinned", required = false) boolean pinned,
                                       @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
                                       @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {
        final Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        log.trace("Endpoint request: GET /compilations");
        return compilationService.getAll(pinned, pageable);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.trace("Endpoint request: GET /compilations");
        CompilationDto compilationDto = compilationService.getById(compId);
        return compilationService.getById(compId);
    }
}
