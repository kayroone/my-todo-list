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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void createTodoWithValidData() throws Exception {

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

        // 2. Action/Assert:
        when(this.todosService.createTodo(any(TodoBase.class))).thenReturn(Optional.of(todoFull));

        this.mvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.description", is(todoFull.getDescription())))
            .andExpect(jsonPath("$.dueDate", is(todoFull.getDueDate().toString())))
            .andExpect(jsonPath("$.title", is(todoFull.getTitle()))).andExpect(jsonPath("$.done", is(todoFull.isDone())));
    }

    @Test
    public void createTodoWithIncompleteData() throws Exception {

        // 1. Arrange:
        TodoBase todoBase = new TodoBase();

        todoBase.setDescription("foo");
        todoBase.setDone(false);

        OffsetDateTime dateTime = OffsetDateTime.now(Clock.systemDefaultZone());
        todoBase.setDueDate(dateTime);

        String requestJsonBody = objectMapper.writeValueAsString(todoBase);

        TodoFull todoFull = new TodoFull(todoBase);
        todoFull.setId(1);

        // 2. Action/Assert:
        when(this.todosService.createTodo(any(TodoBase.class))).thenReturn(Optional.of(todoFull));

        this.mvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteExistingTodo() throws Exception {

        when(this.todosService.deleteTodo(anyInt())).thenReturn(true);

        this.mvc.perform(delete("/todos/" + 1)).andExpect(status().isNoContent());
    }

    @Test
    public void deleteNoneExistingTodo() throws Exception {

        when(this.todosService.deleteTodo(anyInt())).thenReturn(false);

        this.mvc.perform(delete("/todos/" + 1)).andExpect(status().isNotFound());
    }

    @Test
    public void getExistingTodo() throws Exception {

        // 1. Arrange:
        TodoBase todoBase = new TodoBase();

        todoBase.setDescription("foo");
        todoBase.setDone(false);

        OffsetDateTime dateTime = OffsetDateTime.now(Clock.systemDefaultZone());
        todoBase.setDueDate(dateTime);

        todoBase.setTitle("bar");

        TodoFull todoFull = new TodoFull(todoBase);
        todoFull.setId(1);

        // 2. Action/Assert:
        when(this.todosService.getTodo(todoFull.getId())).thenReturn(Optional.of(todoFull));

        this.mvc.perform(get("/todos/" + todoFull.getId())).andExpect(status().isOk())
            .andExpect(jsonPath("$.description", is(todoFull.getDescription())))
            .andExpect(jsonPath("$.dueDate", is(todoFull.getDueDate().toString())))
            .andExpect(jsonPath("$.title", is(todoFull.getTitle()))).andExpect(jsonPath("$.done", is(todoFull.isDone())));
    }

    @Test
    public void getNoneExistingTodo() throws Exception {

        when(this.todosService.getTodo(anyInt())).thenReturn(Optional.empty());

        this.mvc.perform(get("/todos/" + 1)).andExpect(status().isNotFound());
    }

    @Test
    public void getAllExistingTodos() throws Exception {

        // 1. Arrange
        List<TodoFull> todos = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            TodoBase todoBase = new TodoBase();

            todoBase.setDescription("foo" + i);
            todoBase.setDone(false);

            OffsetDateTime dateTime = OffsetDateTime.now(Clock.systemDefaultZone());
            todoBase.setDueDate(dateTime);

            todoBase.setTitle("bar" + i);

            TodoFull todoFull = new TodoFull(todoBase);
            todoFull.setId(i);

            todos.add(todoFull);
        }

        Page<TodoFull> pagedResponse = new PageImpl(todos);

        // 2. Action/Assert
        when(this.todosService.getTodos(anyString(), anyInt(), anyInt())).thenReturn(pagedResponse);

        this.mvc.perform(get("/todos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    public void getNoneExistingTodos() throws Exception {

        // 1. Arrange
        Page<TodoFull> pagedResponse = mock(Page.class);

        // 2. Action/Assert
        when(this.todosService.getTodos(anyString(), anyInt(), anyInt())).thenReturn(pagedResponse);
        when(pagedResponse.hasContent()).thenReturn(false);

        this.mvc.perform(get("/todos")).andExpect(status().isNoContent());
    }

    @Test
    public void getPartialExistingTodos() throws Exception {

        // 1. Arrange
        Page<TodoFull> pagedResponse = mock(Page.class);

        // 2. Action/Assert
        when(this.todosService.getTodos(anyString(), anyInt(), anyInt())).thenReturn(pagedResponse);
        when(pagedResponse.hasContent()).thenReturn(true);
        when(pagedResponse.getTotalPages()).thenReturn(2);

        this.mvc.perform(get("/todos")).andExpect(status().isPartialContent());
    }

    @Test
    public void updateExistingTodo() throws Exception {

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

        // 2. Action/Assert:
        when(this.todosService.updateTodo(anyInt(), any(TodoBase.class))).thenReturn(Optional.of(todoFull));

        this.mvc.perform(put("/todos/" + 1).contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isNoContent());
    }

    @Test
    public void updateNoneExistingTodo() throws Exception {

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

        // 2. Action/Assert:
        when(this.todosService.updateTodo(anyInt(), any(TodoBase.class))).thenReturn(Optional.empty());

        this.mvc.perform(put("/todos/" + 1).contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isNotFound());
    }
}