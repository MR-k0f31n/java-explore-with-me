package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.input.NewCompilationDto;
import ru.practicum.dto.input.UpdateCompilationDto;
import ru.practicum.service.CompilationService;

import javax.validation.Valid;

/**
 * @author MR.k0F31N
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class CompilationControllerAdmin {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto compilationDto) {
        log.trace("Endpoint request: POST /admin/compilations");
        log.debug("Param: input body '{}'", compilationDto);
        return compilationService.create(compilationDto);
    }

    @PatchMapping("/{compId}")
    public CompilationDto update(@RequestBody @Valid UpdateCompilationDto update,
                                 @PathVariable Long compId) {
        log.trace("Endpoint request: PATCH /admin/compilations/{compId}");
        log.debug("Param: input body '{}', input variable '{}'", update, compId);
        return compilationService.update(compId, update);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compId) {
        log.trace("Endpoint request: DELETE /admin/compilations/{compId}");
        log.debug("Param: input variable '{}'", compId);
        compilationService.delete(compId);
    }
}
