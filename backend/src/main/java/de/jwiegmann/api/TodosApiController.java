package de.jwiegmann.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.jwiegmann.model.TodoBase;
import de.jwiegmann.model.TodoFull;
import de.jwiegmann.model.TodoList;
import de.jwiegmann.service.TodosService;
import de.jwiegmann.util.TodoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Main To Do controller. Provides To Do CRUD operations as API endpoint.
 *
 * Allowing CORS requests from frontend application at http://localhost:8080.
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-15T21:20:24.293Z")
@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class TodosApiController implements TodosApi {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final TodosService todosService;

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(request);
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
     * Delete all existing To Do items.
     *
     * @return HTTP status 204 for a successful deletion or HTTP status 404 if the To Do items could not be found.
     */
    @Override
    public ResponseEntity<Void> deleteAllTodos() {

        boolean successfulDeleted = this.todosService.deleteAll();

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
     * Get all To Do items sorted by their ID (ASC) as pageable.
     *
     * @param state  State of the returned To Do items (finished, unfinished, all).
     * @param limit  Maximum To Do items per page.
     * @param offset Define the number from which elements are to be returned.
     *
     * @return HTTP status 200 with a list of To Do items as JSON body, HTTP status 206 with the partial requested
     * To Do items, HTTP status 204 if there are no To Do items, or HTTP status 400 for invalid query params.
     */
    @Override
    public ResponseEntity<List<TodoList>> getTodos(@RequestParam(required = false, defaultValue = "unfinished") String state,
        @RequestParam(required = false, defaultValue = "5") Integer limit,
        @RequestParam(required = false, defaultValue = "0") Integer offset)
    {

        Page<TodoFull> foundTodosPage = this.todosService.getTodos(state, limit, offset);

        // No items found:
        if (!foundTodosPage.hasContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // More items found than specified:
        if (foundTodosPage.getTotalPages() > 1) {
            return new ResponseEntity<List<TodoList>>(TodoUtil.mapFullToList(foundTodosPage), HttpStatus.PARTIAL_CONTENT);
        }

        // Items found within specified range:
        else if (foundTodosPage.getTotalPages() == 1) {
            return new ResponseEntity<List<TodoList>>(TodoUtil.mapFullToList(foundTodosPage), HttpStatus.OK);
        }

        // Ententies can not be processed:
        else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Update an existing To Do item.
     *
     * @param todoId The To Do item ID
     * @param body   The JSON body holding the changes.
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
