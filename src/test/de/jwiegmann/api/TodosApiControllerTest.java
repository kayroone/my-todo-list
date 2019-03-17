package de.jwiegmann.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.jwiegmann.model.TodoBase;
import de.jwiegmann.model.TodoFull;
import de.jwiegmann.service.TodosService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TodosApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodosService todosService;

    @Test
    public void createTodoAndCheckResponse() throws Exception {

        // 1. Arrange:
        TodoBase todoBase = new TodoBase();

        todoBase.setDescription("foo");
        todoBase.setDone(false);

        OffsetDateTime dateTime = OffsetDateTime.now(Clock.systemDefaultZone());
        todoBase.setDueDate(dateTime);

        todoBase.setTitle("bar");

        String requestJsonBody = objectMapper.writeValueAsString(todoBase);

        TodoFull todoFull = new TodoFull(todoBase);
        todoFull.setId(1);

        when(this.todosService.createTodo(any(TodoBase.class))).thenReturn(Optional.of(todoFull));

        this.mvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.description", is(todoFull.getDescription())))
            .andExpect(jsonPath("$.dueDate", is(todoFull.getDueDate().toString())))
            .andExpect(jsonPath("$.title", is(todoFull.getTitle()))).andExpect(jsonPath("$.done", is(todoFull.isDone())));
    }
}