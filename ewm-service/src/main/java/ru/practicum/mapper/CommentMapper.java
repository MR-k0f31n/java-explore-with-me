package ru.practicum.mapper;

import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.input.NewCommentDto;
import ru.practicum.model.Comment;

import static ru.practicum.mapper.EventMapper.toShortDto;
import static ru.practicum.mapper.UserMapper.toShortDto;

/**
 * @author MR.k0F31N
 */

public class CommentMapper {
    public static Comment toModel(NewCommentDto input) {
        return Comment.builder()
                .text(input.getText())
                .build();
    }

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .event(toShortDto(comment.getEvent()))
                .author(toShortDto(comment.getAuthor()))
                .createdOn(comment.getCreatedDate())
                .text(comment.getText())
                .build();
    }
}
