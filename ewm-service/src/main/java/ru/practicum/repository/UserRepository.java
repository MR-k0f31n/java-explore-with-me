package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.User;

import java.util.List;

/**
 * @author MR.k0F31n
 */
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdIn(Long[] ids, Pageable pageable);

    Boolean existsByEmail(String email);
}
