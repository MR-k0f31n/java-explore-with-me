package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.input.NewCompilationDto;
import ru.practicum.dto.input.UpdateCompilationDto;

import java.util.List;

/**
 * @author MR.k0F31N
 */

public interface CompilationService {
    CompilationDto create(NewCompilationDto compilationDto);

    CompilationDto update(Long compId, UpdateCompilationDto compilationDto);

    void delete(Long compId);

    List<CompilationDto> getAll(boolean pinned, Pageable pageable);

    CompilationDto getById(Long compId);
}
