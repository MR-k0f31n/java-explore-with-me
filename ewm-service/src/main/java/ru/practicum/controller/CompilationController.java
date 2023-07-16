package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.input.NewCompilationDto;
import ru.practicum.dto.input.UpdateCompilationDto;
import ru.practicum.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author MR.k0F31N
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class CompilationController {
    private final CompilationService compilationService;

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto compilationDto) {
        log.trace("Endpoint request: POST /admin/compilations");
        log.debug("Param: input body '{}'", compilationDto);
        return compilationService.create(compilationDto);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto update(@RequestBody @Valid UpdateCompilationDto update,
                                 @PathVariable Long compId) {
        log.trace("Endpoint request: PATCH /admin/compilations/{compId}");
        log.debug("Param: input body '{}', input variable '{}'", update, compId);
        return compilationService.update(compId, update);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compId) {
        log.trace("Endpoint request: DELETE /admin/compilations/{compId}");
        log.debug("Param: input variable '{}'", compId);
        compilationService.delete(compId);
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getAll(@RequestParam(value = "pinned", required = false) boolean pinned,
                                       @RequestParam(name = "from", defaultValue = "0")@Min(0) Integer from,
                                       @RequestParam(name = "size", defaultValue = "10")@Min(1) Integer size) {
        final Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        log.trace("Endpoint request: GET /compilations");
        return compilationService.getAll(pinned, pageable);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.trace("Endpoint request: GET /compilations");
        CompilationDto compilationDto = compilationService.getById(compId);
        return compilationService.getById(compId);
    }
}
