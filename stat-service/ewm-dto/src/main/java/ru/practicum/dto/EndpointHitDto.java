package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author MR.k0F31n
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitDto {
    /**
     * Идентификатор записи
     */
    private Long id;

    /**
     * Идентификатор сервиса для которого записывается информация
     */
    private String app;

    /**
     * URI для которого был осуществлен запрос
     */
    private String uri;

    /**
     * IP-адрес пользователя, осуществившего запрос
     */
    private String ip;

    /**
     * Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
     */
    private LocalDateTime timestamp;
}
