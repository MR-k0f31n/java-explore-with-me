package ru.practicum.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.controller.CommentControllerPublic;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.service.CommentService;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author MR.k0F31n
 */
@WebMvcTest(CommentControllerPublic.class)
@AutoConfigureMockMvc
public class CommentControllerPublicTest {
    @MockBean
    private CommentService commentService;
    @Autowired
    private MockMvc mockMvc;

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
        when(commentService.getAllCommentsFromEvent(anyLong(), any(Pageable.class)))
                .thenReturn(List.of(comment1, comment2, comment3));

        mockMvc.perform(get("/event/{eventId}/comments", 1L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(comment1.getId()), Long.class))
                .andExpect(jsonPath("$[2].id", is(comment3.getId()), Long.class));

        verify(commentService, times(1)).getAllCommentsFromEvent(anyLong(), any(Pageable.class));
    }
}
