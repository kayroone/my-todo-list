package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.TodoBase;
import io.swagger.model.TodoFull;
import io.swagger.model.TodoList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import service.TodosService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-14T13:19:45.394Z")

@RestController
public class TodosApiController implements TodosApi {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final TodosService todosService;

    @org.springframework.beans.factory.annotation.Autowired
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

        return null;
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

        return null;
    }

    /**
     * Get all existing To Do items.
     *
     * @param state  State of the To Do item (e.g. unfished).
     * @param limit  Limit the number of To Do items returned.
     * @param offset Define the number from which elements are to be returned.
     *
     * @return HTTP status 200 with a list of To Do items as JSON body, HTTP status 204 if there are no To Do items,
     * HTTP status 206 with the partial requested To Do items or HTTP status 400 for invalid query params.
     */
    @Override
    public ResponseEntity<List<TodoList>> getTodos(String state, Integer limit, Integer offset) {

        return null;
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
    public ResponseEntity<Void> updateTodo(Integer todoId, TodoBase body) {
        return null;
    }
}
