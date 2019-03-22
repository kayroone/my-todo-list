package de.jwiegmann.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.jwiegmann.app.RFC3339DateFormat;
import de.jwiegmann.model.TodoBase;
import de.jwiegmann.model.TodoFull;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Iterator;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodosApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoBase todoBase;
    private RFC3339DateFormat dateFormat;

    /**
     * Test values
     */

    private static final String testDescription = "foo";
    private static final Boolean testDoneValue = false;
    private static final String testDueDate = "2019-03-18T10:20:43.563Z";
    private static final String testTitle = "bar";

    @Before
    public void setUp() {

        // Set correct date format:
        this.dateFormat = new RFC3339DateFormat();

        // Prepare object mapper:
        this.objectMapper.setDateFormat(this.dateFormat);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Initialize test To Do object:
        this.initTestTodo();
    }

    @Test
    public void createTodoWithValidData() throws Exception {

        // 1. Arrange:
        String requestJsonBody = this.objectMapper.writeValueAsString(this.todoBase);

        // 2. Action/Assert:
        this.mvc.perform(post("/todos")
            .contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description", is(testDescription)))
            .andExpect(jsonPath("$.dueDate", is(testDueDate)))
            .andExpect(jsonPath("$.title", is(testTitle)))
            .andExpect(jsonPath("$.done", is(testDoneValue)));

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void createTodoWithMissingData() throws Exception {

        // 1. Arrange:
        String requestJsonBody = this.objectMapper.writeValueAsString(this.todoBase);

        JSONObject jsonObject = new JSONObject(requestJsonBody);
        JSONObject requestObject = new JSONObject(requestJsonBody);

        Iterator keys = jsonObject.keys();

        // 2. Action/Assert
        while (keys.hasNext()) {

            String key = (String) keys.next();
            String value = requestObject.getString(key);

            requestObject.remove(key);

            if (key.equalsIgnoreCase("description")) {
                this.mvc.perform(post("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestObject.toString()))
                    .andExpect(status().isCreated());
            } else {
                this.mvc.perform(post("/todos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestObject.toString()))
                    .andExpect(status().isBadRequest());
            }

            requestObject.put(key, value);
        }

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void createTodoWithLongTitle() throws Exception {

        // 1. Arrange:
        this.todoBase.setTitle("TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        String requestJsonBody = this.objectMapper.writeValueAsString(this.todoBase);

        // 2. Action/Assert:
        this.mvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteExistingTodo() throws Exception {

        // 1. Arrange:
        TodoFull todoFull = createToDo();

        // 2. Action/Assert:
        this.mvc.perform(delete("/todos/" + todoFull.getId()))
            .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNoneExistingTodo() throws Exception {

        // 1. Action/Assert:
        this.mvc.perform(delete("/todos/" + 4))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAllTodos() throws Exception {

        // 1. Action/Assert:
        this.mvc.perform(delete("/todos/"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void getExistingTodo() throws Exception {

        // 1. Arrange:
        TodoFull todoFull = createToDo();

        // 2. Action/Assert:
        this.mvc.perform(get("/todos/" + todoFull.getId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description", is(testDescription)))
            .andExpect(jsonPath("$.dueDate", is("2019-03-18T11:20:43.563+01:00")))
            .andExpect(jsonPath("$.title", is(testTitle)))
            .andExpect(jsonPath("$.done", is(testDoneValue)));

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void getNoneExistingTodo() throws Exception {

        // 1. Action/Assert:
        this.mvc.perform(get("/todos/" + 9000))
            .andExpect(status().isNotFound());
    }

    @Test
    public void getAllExistingTodos() throws Exception {

        // 1. Arrange:
        for (int i = 0; i < 5; i++) {
            createToDo();
        }
        for (int i = 0; i < 3; i++) {
            this.todoBase.setDone(true);
            createToDo();
        }

        // 2. Action/Assert:
        this.mvc.perform(get("/todos?state=all&limit=8")).andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(8)));

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void getAllExistingTodosPartial() throws Exception {

        // 1. Arrange:
        for (int i = 0; i < 8; i++) {
            createToDo();
        }

        // 2. Action/Assert:
        this.mvc.perform(get("/todos?state=all"))
            .andExpect(status().isPartialContent())
            .andExpect(jsonPath("$", hasSize(5)));

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void getAllExistingUnfinishedTodos() throws Exception {

        // 1. Arrange:
        for (int i = 0; i < 5; i++) {
            createToDo();
        }
        for (int i = 0; i < 3; i++) {
            this.todoBase.setDone(true);
            createToDo();
        }

        // 2. Action/Assert:
        this.mvc.perform(get("/todos?state=unfinished"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)));

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void getAllExistingUnfinishedTodosPartial() throws Exception {

        // 1. Arrange:
        for (int i = 0; i < 8; i++) {
            createToDo();
        }
        for (int i = 0; i < 3; i++) {
            this.todoBase.setDone(true);
            createToDo();
        }

        // 2. Action/Assert:
        this.mvc.perform(get("/todos?state=unfinished")).andExpect(status().isPartialContent())
            .andExpect(jsonPath("$", hasSize(5)));

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void getNoneExistingTodos() throws Exception {

        // 1. Arrange:
        removeAllToDos();

        // 2. Action/Assert:
        this.mvc.perform(get("/todos"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void updateExistingTodo() throws Exception {

        // 1. Arrange:
        TodoFull todoFull = createToDo();

        this.todoBase.setDescription("barfoo");
        this.todoBase.setTitle("foobar");
        this.todoBase.setDone(true);
        this.todoBase.setDueDate(getDateTime("2019-03-16T20:14:57.445Z"));

        String requestJsonBody = this.objectMapper.writeValueAsString(this.todoBase);

        // 2. Action/Assert:
        this.mvc.perform(put("/todos/" + todoFull.getId())
            .contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isNoContent());

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void updateTodoWithMissingData() throws Exception {

        // 1. Arrange:
        TodoFull todoFull = createToDo();
        int todoId = todoFull.getId();

        String requestJsonBody = this.objectMapper.writeValueAsString(todoFull);

        JSONObject jsonObject = new JSONObject(requestJsonBody);
        JSONObject requestObject = new JSONObject(requestJsonBody);

        Iterator keys = jsonObject.keys();

        // 2. Action/Assert
        while (keys.hasNext()) {

            String key = (String) keys.next();
            String value = requestObject.getString(key);

            requestObject.remove(key);

            if (key.equalsIgnoreCase("description") ||
                key.equalsIgnoreCase("id")) {
                this.mvc.perform(put("/todos/" + todoId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestObject.toString()))
                    .andExpect(status().isNoContent());
            } else {
                this.mvc.perform(put("/todos/" + todoId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestObject.toString()))
                    .andExpect(status().isBadRequest());
            }

            requestObject.put(key, value);
        }

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void updateExistingTodoWithNoIdInRequest() throws Exception {

        // 1. Arrange:
        createToDo();

        this.todoBase.setDescription("barfoo");
        this.todoBase.setTitle("foobar");
        this.todoBase.setDone(true);
        this.todoBase.setDueDate(getDateTime("2019-03-16T20:14:57.445Z"));

        String requestJsonBody = this.objectMapper.writeValueAsString(this.todoBase);

        // 2. Action/Assert:
        this.mvc.perform(put("/todos")
            .contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isMethodNotAllowed());

        // 3. Cleanup:
        removeAllToDos();
    }

    @Test
    public void updateNoneExistingTodo() throws Exception {

        // 1. Arrange:
        this.todoBase.setDescription("barfoo");
        this.todoBase.setTitle("foobar");
        this.todoBase.setDone(true);
        this.todoBase.setDueDate(getDateTime("2019-03-16T20:14:57.445Z"));

        String requestJsonBody = this.objectMapper.writeValueAsString(this.todoBase);

        // 2. Action/Assert:
        this.mvc.perform(put("/todos/" + 4).contentType(MediaType.APPLICATION_JSON).content(requestJsonBody))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTodoWithLongTitle() throws Exception {

        // 1. Arrange:
        TodoFull todoFull = createToDo();

        this.todoBase.setTitle("TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        String requestJsonBody = this.objectMapper.writeValueAsString(this.todoBase);

        // 2. Action/Assert:
        this.mvc.perform(put("/todos/" + todoFull.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJsonBody))
            .andExpect(status().isBadRequest());
    }

    // TEST HELPERS --------------------------------------------------------------------------------------------------------


    private void initTestTodo() {

        this.todoBase = new TodoBase();

        this.todoBase.setDescription(testDescription);
        this.todoBase.setDone(testDoneValue);
        this.todoBase.setDueDate(getDateTime(testDueDate));
        this.todoBase.setTitle(testTitle);
    }

    private OffsetDateTime getDateTime(String dateString) {

        Date date = null;
        try {
            date = this.dateFormat.parse(dateString);
        } catch (ParseException pe) {
            // Ignore
        }

        OffsetDateTime dateTime = null;
        if (date != null) {
            dateTime = date.toInstant().atOffset(ZoneOffset.UTC);
        }

        return dateTime;
    }

    private TodoFull createToDo() throws Exception {

        String jsonRequestString = this.objectMapper.writeValueAsString(this.todoBase);

        MvcResult mvcResult = this.mvc.perform(post("/todos")
            .content(jsonRequestString)
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String jsonResponseString = mvcResult.getResponse().getContentAsString();

        return this.objectMapper.readValue(jsonResponseString, TodoFull.class);
    }

    private void removeAllToDos() throws Exception {

        this.mvc.perform(delete("/todos"))
            .andExpect(status().isNoContent());
    }
}