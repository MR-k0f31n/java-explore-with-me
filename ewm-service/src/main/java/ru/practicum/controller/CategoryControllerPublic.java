package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.service.CategoryService;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author MR.k0F31n
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/categories")
public class CategoryControllerPublic {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAll(@RequestParam(value = "from", required = false, defaultValue = "0") @Min(0) Integer from,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") @Min(1) Integer size) {
        log.trace("Endpoint request: GET /categories");
        log.debug("Param: from = '{}', size = '{}'", from, size);
        final Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        return categoryService.getAll(pageable);
    }

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getById(@PathVariable(value = "catId") @Min(1) Long catId) {
        log.trace("Endpoint request: GET /categories/{catId}");
        log.debug("Param: categoryId = '{}'", catId);
        return categoryService.getById(catId);
    }
}