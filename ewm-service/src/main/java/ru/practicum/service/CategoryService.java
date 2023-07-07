package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.input.NewCategoryDto;

import java.util.List;

/**
 * @author MR.k0F31n
 */
public interface CategoryService {
    CategoryDto created(NewCategoryDto categoryDto);

    void delete(Long catId);

    CategoryDto update(Long catId, NewCategoryDto categoryDto);

    List<CategoryDto> getAll(Pageable pageable);

    CategoryDto getById(Long catId);
}
