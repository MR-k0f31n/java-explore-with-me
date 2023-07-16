package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.input.NewCompilationDto;
import ru.practicum.dto.input.UpdateCompilationDto;
import ru.practicum.exception.IncorrectParametersException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidatedException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.CompilationService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mapper.CompilationMapper.toDto;
import static ru.practicum.mapper.CompilationMapper.toModel;

/**
 * @author MR.k0F31N
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;


    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto compilationDto) {
        final Compilation compilation = toModel(compilationDto);
        if (compilation.getPinned() == null) compilation.setPinned(false);
        if (compilationDto.getEvents() != null) {
            final List<Event> getEvent = eventRepository.findAllById(compilationDto.getEvents());
            compilation.setEvents(getEvent);
        } else {
            compilation.setEvents(new ArrayList<>());
        }
        final Compilation compilationAfterSave = compilationRepository.save(compilation);
        log.debug("Compilation save = [{}]", compilationAfterSave.toString());
        return toDto(compilationAfterSave);
    }

    @Override
    public CompilationDto update(Long compId, UpdateCompilationDto update) {
        final Compilation compilation = getCompilationById(compId);
        if (update.getEvents() != null) {
            compilation.setEvents(update.getEvents().stream()
                    .flatMap(ids -> eventRepository.findAllById(Collections.singleton(ids))
                            .stream())
                    .collect(Collectors.toList()));
        }
        compilation.setPinned(update.getPinned() != null ? update.getPinned() : compilation.getPinned());
        compilation.setTitle(update.getTitle() != null ? update.getTitle() : compilation.getTitle());
        final Compilation compilationAfterSave = compilationRepository.save(compilation);
        return toDto(compilationAfterSave);
    }

    @Override
    public void delete(Long compId) {
        getCompilationById(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public List<CompilationDto> getAll(boolean pinned, Pageable pageable) {
        return compilationRepository.findAllByPinnedIs(pinned, pageable)
                .stream().map(CompilationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getById(Long compId) {
        return toDto(getCompilationById(compId));
    }

    private Compilation getCompilationById(Long comId) {
        return compilationRepository.findById(comId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation with id = '%d' not found", comId)));
    }
}
