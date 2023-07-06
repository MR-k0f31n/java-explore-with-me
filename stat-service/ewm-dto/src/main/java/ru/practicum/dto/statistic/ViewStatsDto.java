package ru.practicum.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author MR.k0F31n
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsDto {
    /**
     * Название сервиса
     */
    private String app;
    /**
     * URI сервиса
     */
    private String uri;
    /**
     * Количество просмотров
     */
    private Long hits;
}
