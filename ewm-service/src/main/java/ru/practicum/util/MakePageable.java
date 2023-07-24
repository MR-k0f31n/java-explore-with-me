package ru.practicum.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author MR.k0F31N
 */

public class MakePageable {
    public static Pageable createPageable(Integer from, Integer size, Sort.Direction sort, String sortBy) {
        return PageRequest.of(from / size, size, Sort.by(sort, sortBy));
    }
}
