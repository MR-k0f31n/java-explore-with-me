package ru.practicum.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.controller.CommentControllerPublic;
import ru.practicum.controller.users.CommentControllerUsers;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.input.NewCommentDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.service.CommentService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author MR.k0F31N
 */

@WebMvcTest(CommentControllerUsers.class)
@AutoConfigureMockMvc
public class CommentControllerUsersTest {
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
    private final NewCommentDto newComment = NewCommentDto.builder().text("Первый комментарий!").build();

    @Test
    public void createNewComment_returnCommentDto() throws Exception {
        when(commentService.createNewComment(anyLong(), anyLong(), any(NewCommentDto.class))).thenReturn(comment1);

        mockMvc.perform(post("/users/{userId}/comments", 1)
                        .content(mapper.writeValueAsString(newComment))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("eventId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(comment1.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(comment1.getText())));

        verify(commentService, times(1)).createNewComment(anyLong(), anyLong(), any(NewCommentDto.class));
    }

    @Test
    public void createNewComment_expectedBadRequest() throws Exception {
        when(commentService.createNewComment(99L, 99L, newComment)).thenReturn(comment1);

        mockMvc.perform(post("/users/{userId}/comments", 1)
                        .content(mapper.writeValueAsString(any(NewCommentDto.class)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("eventId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //verify(commentService, times(0)).createNewComment(anyLong(), anyLong(), any(NewCommentDto.class));
    }
}
