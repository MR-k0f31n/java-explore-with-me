package ru.practicum.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.service.CommentService;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author MR.k0F31n
 */
public class CommentControllerTest {
    @MockBean
    private CommentService commentService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private final CommentDto comment1 = CommentDto.builder()
            .id(1L)
            .text("Первый комментарий!")
            .author(UserShortDto.builder().id(1L).name("Name1").build())
            .event(EventShortDto.builder().id(1L).title("New event1").build())
            .build();

    private final CommentDto comment2 = CommentDto.builder()
            .id(2L)
            .text("Второй комментарий!")
            .author(UserShortDto.builder().id(2L).name("Name2").build())
            .event(EventShortDto.builder().id(1L).title("New event1").build())
            .build();

    private final CommentDto comment3 = CommentDto.builder()
            .id(3L)
            .text("Третий комментарий!")
            .author(UserShortDto.builder().id(3L).name("Name3").build())
            .event(EventShortDto.builder().id(1L).title("New event1").build())
            .build();

    @Test
    void getAllCommentByEvent_returnCommentDtoList() throws Exception {
        when(commentService.getAllCommentsFromEvent(1L, any(Pageable.class)))
                .thenReturn(List.of(comment1, comment2, comment3));

        mockMvc.perform(get("event/{eventId}/comments", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]."))
    }
}
