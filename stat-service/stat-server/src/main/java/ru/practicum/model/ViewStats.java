package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author MR.k0F31n
 */
@Data
@AllArgsConstructor
@Builder
public class ViewStats {
    /**
     * Название сервиса
     */
    private String app;
    /**
     * URI сервиса
     */
    private String uri;
    /**
     * Количество просмотров count
     */
    private Integer hits;
}
