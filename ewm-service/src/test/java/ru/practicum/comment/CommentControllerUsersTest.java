package ru.practicum.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.controller.users.CommentControllerUsers;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.input.NewCommentDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.service.CommentService;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author MR.k0F31N
 */

@WebMvcTest(CommentControllerUsers.class)
@AutoConfigureMockMvc
public class CommentControllerUsersTest {
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
    private final NewCommentDto newComment = NewCommentDto.builder().text("Первый комментарий!").build();
    private final NewCommentDto update = NewCommentDto.builder().text("Обновленный текст").build();
    @MockBean
    private CommentService commentService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void createNewComment_returnCommentDto() throws Exception {
        when(commentService.createNewComment(anyLong(), anyLong(), any(NewCommentDto.class)))
                .thenReturn(comment1);

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
    public void getAllCommentsByUser_returnListSize3() throws Exception {
        when(commentService.getAllCommentsFromUser(anyLong(), any(Pageable.class)))
                .thenReturn(Arrays.asList(comment1, comment2, comment3));

        mockMvc.perform(get("/users/{userId}/comments", 1L, Long.class))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(comment1.getId()), Long.class))
                .andExpect(jsonPath("$[2].id", is(comment3.getId()), Long.class))
                .andExpect(jsonPath("$[0].text", is(comment1.getText())))
                .andExpect(jsonPath("$[2].text", is(comment3.getText())));

        verify(commentService, times(1)).getAllCommentsFromUser(anyLong(), any(Pageable.class));
    }


    @Test
    void updateComment_returnCommentDto() throws Exception {
        final CommentDto commentAfterUpdate = comment1;
        commentAfterUpdate.setText(update.getText());

        when(commentService.updateComment(anyLong(), anyLong(), any(NewCommentDto.class))).thenReturn(commentAfterUpdate);

        mockMvc.perform(patch("/users/{userId}/comments/{commentId}", 1L, 1L)
                        .content(mapper.writeValueAsString(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1L), Long.class))
                .andExpect(jsonPath("$.text", is(update.getText())));

        verify(commentService, times(1)).updateComment(anyLong(), anyLong(), any(NewCommentDto.class));
    }
}
