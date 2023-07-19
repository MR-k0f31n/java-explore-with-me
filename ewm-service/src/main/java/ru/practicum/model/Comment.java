package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author MR.k0F31n
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comments")
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment", length = 7000, nullable = false)
    private String text;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id")
    private Event event;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
