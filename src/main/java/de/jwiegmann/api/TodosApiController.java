package de.jwiegmann.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.jwiegmann.model.TodoBase;
import de.jwiegmann.model.TodoFull;
import de.jwiegmann.service.TodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-14T13:19:45.394Z")

@RestController
public class TodosApiController implements TodosApi {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final TodosService todosService;

    @Autowired
    public TodosApiController(ObjectMapper objectMapper, HttpServletRequest request, TodosService todosService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.todosService = todosService;
    }

    /**
     * Create a new To Do item.
     *
     * @param body JSON body.
     *
     * @return HTTP status 201 with the new To Do item as JSON body or HTTP status 422 if the entity could not
     * be processed.
     */
    @Override
    public ResponseEntity<TodoFull> createTodo(TodoBase body) {

        Optional<TodoFull> newTodo = this.todosService.createTodo(body);

        if (newTodo.isPresent()) {
            return new ResponseEntity<TodoFull>(newTodo.get(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<TodoFull>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Delete an existing To Do item.
     *
     * @param todoId The To Do item ID.
     *
     * @return HTTP status 204 for a successful deletion or HTTP status 404 if the To Do item could not be found.
     */
    @Override
    public ResponseEntity<Void> deleteTodo(Integer todoId) {

        boolean successfulDeleted = this.todosService.deleteTodo(todoId);

        if (successfulDeleted) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get a single To Do item.
     *
     * @param todoId The To Do item ID.
     *
     * @return HTTP status 200 with the To Do item as JSON body or HTTP status 404 if the To Do item could not be found.
     */
    @Override
    public ResponseEntity<TodoFull> getTodo(Integer todoId) {

        Optional<TodoFull> existingTodo = this.todosService.getTodo(todoId);

        if (existingTodo.isPresent()) {
            return new ResponseEntity<TodoFull>(existingTodo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all existing To Do items.
     *
     * @param state  State of the To Do item (e.g. unfinished).
     * @param limit  Limit the number of To Do items returned.
     * @param offset Define the number from which elements are to be returned.
     *
     * @return HTTP status 200 with a list of To Do items as JSON body, HTTP status 206 with the partial requested
     * To Do items, HTTP status 204 if there are no To Do items, or HTTP status 400 for invalid query params.
     */
    @Override
    public ResponseEntity getTodos(@RequestParam(required = false, defaultValue = "unfinished") String state,
        @Min(0) @Max(10) @RequestParam(required = false, defaultValue = "5") Integer limit,
        @Min(0) @Max(100) @RequestParam(required = false) Integer offset)
    {

        Page<TodoFull> foundTodos = this.todosService.getTodos(state, limit, offset);

        // Without offset - maximum result of 5 items:
        if (foundTodos.hasContent() && offset == null) {
            return new ResponseEntity<Page<TodoFull>>(foundTodos, HttpStatus.OK);
        }

        // With offset:
        else if (foundTodos.hasContent() && offset != null) {
            return new ResponseEntity<Page<TodoFull>>(foundTodos, HttpStatus.PARTIAL_CONTENT);
        }

        // No items found:
        else if (!foundTodos.hasContent()) {
            return new ResponseEntity<>(foundTodos, HttpStatus.NO_CONTENT);

        }

        // Entities could not be processed:
        else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Update an existing To Do item.
     *
     * @param todoId The To Do item ID
     * @param body   The JSON body containing the changes.
     *
     * @return HTTP status 204 with the updated To Do items as JSON body,
     * HTTP status 400 if there was an error modifying the To Do item or HTTP status 404 if the To Do item could not be found.
     */
    @Override
    public ResponseEntity<TodoFull> updateTodo(Integer todoId, TodoBase body) {

        Optional<TodoFull> updatedTodo = this.todosService.updateTodo(todoId, body);

        if (updatedTodo.isPresent()) {
            return new ResponseEntity<>(updatedTodo.get(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(updatedTodo.get(), HttpStatus.NOT_FOUND);
        }
    }
}
