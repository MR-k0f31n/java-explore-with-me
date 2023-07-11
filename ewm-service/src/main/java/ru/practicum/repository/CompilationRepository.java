package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Compilation;

/**
 * @author MR.k0F31n
 */
@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
