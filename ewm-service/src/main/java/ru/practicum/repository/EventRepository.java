package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Event;

/**
 * @author MR.k0F31n
 */
public interface EventRepository extends JpaRepository<Event, Long> {
}
