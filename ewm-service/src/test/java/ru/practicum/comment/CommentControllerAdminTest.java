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
import ru.practicum.controller.admin.CommentControllerAdmin;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.service.CommentService;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author MR.k0F31N
 */

@WebMvcTest(CommentControllerAdmin.class)
@AutoConfigureMockMvc
public class CommentControllerAdminTest {
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
    @MockBean
    private CommentService commentService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getAllCommentByText_returnListDto() throws Exception {
        when(commentService.searchCommentByText(any(String.class), any(Pageable.class)))
                .thenReturn(Arrays.asList(comment1, comment2, comment3));

        mockMvc.perform(get("/admin/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("text", "коммент")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text", is(comment1.getText())))
                .andExpect(jsonPath("$[2].text", is(comment3.getText())));

    }
}
