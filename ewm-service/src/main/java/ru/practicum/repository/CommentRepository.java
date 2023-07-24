package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Comment;

import java.util.List;

/**
 * @author MR.k0F31N
 */

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthorId(Long userId, Pageable pageable);

    List<Comment> findAllByEventId(Long eventId, Pageable pageable);

    void deleteByIdAndAuthorId(Long commentId, Long userId);

    List<Comment> findAllByTextContainingIgnoreCase(String text, Pageable pageable);
}
