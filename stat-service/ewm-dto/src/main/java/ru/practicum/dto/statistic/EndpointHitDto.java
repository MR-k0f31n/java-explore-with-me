package ru.practicum.dto.statistic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotEmpty(message = "Name app is null")
    private String app;

    /**
     * URI для которого был осуществлен запрос
     */
    @NotEmpty(message = "uri is null")
    private String uri;

    /**
     * IP-адрес пользователя, осуществившего запрос
     */
    @NotEmpty(message = "IP is null")
    private String ip;

    /**
     * Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Timestamp is null")
    private LocalDateTime timestamp;
}
