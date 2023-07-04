package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author MR.k0F31n
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "stats")
public class EndpointHit {
    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор сервиса для которого записывается информация
     */
    @Column(nullable = false)
    private String app;

    /**
     * URI для которого был осуществлен запрос
     */
    @Column(nullable = false)
    private String uri;

    /**
     * IP-адрес пользователя, осуществившего запрос
     */
    @Column(nullable = false)
    private String ip;

    /**
     * Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
     */
    @Column(name = "created", nullable = false)
    private LocalDateTime timestamp;
}
