/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.2).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.model.ErrorResponse;
import io.swagger.model.TodoBase;
import io.swagger.model.TodoFull;
import io.swagger.model.TodoList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-14T13:19:45.394Z")

/**
 * Auto-generated Swagger To Do API endpoint.
 */

@Api(value = "todos", description = "the todos API")
public interface TodosApi {

    Logger log = LoggerFactory.getLogger(TodosApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }
    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }
    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    /**
     * Create a new To Do item.
     *
     * @param body JSON body.
     * @return HTTP status 201 with the new To Do item as JSON body or HTTP status 400 for invalid To Do data.
     */

    @ApiOperation(value = "Create Todo", nickname = "createTodo", notes = "Create a new todo.", response = TodoFull.class, tags={ "Todos", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Todo created.", response = TodoFull.class),
        @ApiResponse(code = 400, message = "Invalid new todo.", response = ErrorResponse.class, responseContainer = "List")
    })
    @RequestMapping(value = "/todos",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<TodoFull> createTodo(@ApiParam(value = "The new todo."  ) @Valid @RequestBody TodoBase body);

    /**
     * Delete an existing To Do item.
     *
     * @param todoId The To Do item ID.
     * @return HTTP status 204 for a successful deletion or HTTP status 404 if the To Do item could not be found.
     */

    @ApiOperation(value = "Delete Todo", nickname = "deleteTodo", notes = "Delete an existing todo.", tags={ "Todos", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Todo deleted."),
        @ApiResponse(code = 404, message = "Todo not found.") })
    @RequestMapping(value = "/todos/{todo-id}",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTodo(@ApiParam(value = "The todo identifier.",required=true) @PathVariable("todo-id") Integer todoId);

    /**
     * Get a single To Do item.
     *
     * @param todoId The To Do item ID.
     * @return HTTP status 200 with the To Do item as JSON body or HTTP status 404 if the To Do item could not be found.
     */

    @ApiOperation(value = "Get Todo", nickname = "getTodo", notes = "Request an existing todo.", response = TodoFull.class, tags={ "Todos", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Todo found.", response = TodoFull.class),
        @ApiResponse(code = 404, message = "Todo not found.") })
    @RequestMapping(value = "/todos/{todo-id}",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<TodoFull> getTodo(@ApiParam(value = "The todo identifier.",required=true) @PathVariable("todo-id") Integer todoId);

    /**
     * Get all existing To Do items.
     *
     * @param state State of the To Do item (e.g. unfished).
     * @param limit Limit the number of To Do items returned.
     * @param offset Define the number from which elements are to be returned.
     * @return HTTP status 200 with a list of To Do items as JSON body, HTTP status 204 if there are no To Do items,
     * HTTP status 206 with the partial requested To Do items or HTTP status 400 for invalid query params.
     */

    @ApiOperation(value = "List todos", nickname = "getTodos", notes = "Get a list of todos.", response = TodoList.class, responseContainer = "List", tags={ "Todos", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of todos.", response = TodoList.class, responseContainer = "List"),
        @ApiResponse(code = 204, message = "Empty list of todos"),
        @ApiResponse(code = 206, message = "Partial list of todos.", response = TodoList.class),
        @ApiResponse(code = 400, message = "Invalid query params", response = ErrorResponse.class, responseContainer = "List") })
    @RequestMapping(value = "/todos",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<TodoList>> getTodos(@ApiParam(value = "Filters all or unfinished todos in the response", allowableValues = "all, unfinished", defaultValue = "unfinished") @Valid @RequestParam(value = "state", required = false, defaultValue="unfinished") String state,@Min(0) @Max(10) @ApiParam(value = "Maximal number of todos in the response", defaultValue = "5") @Valid @RequestParam(value = "limit", required = false, defaultValue="5") Integer limit,@Min(0) @Max(100) @ApiParam(value = "Offset for the todos in the response") @Valid @RequestParam(value = "offset", required = false) Integer offset);

    /**
     * Update an existing To Do item.
     *
     * @param todoId The To Do item ID
     * @param body The JSON body containing the changes.
     * @return HTTP status 204 with the updated To Do items as JSON body,
     * HTTP status 400 if there was an error modifying the To Do item or HTTP status 404 if the To Do item could not be found.
     */

    @ApiOperation(value = "Update Todo", nickname = "updateTodo", notes = "Update an existing todo.", tags={ "Todos", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Todo updated."),
        @ApiResponse(code = 400, message = "Invalid modified todo.", response = ErrorResponse.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Todo not found.") })
    @RequestMapping(value = "/todos/{todo-id}",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateTodo(@ApiParam(value = "The todo identifier.",required=true)
    @PathVariable("todo-id") Integer todoId,@ApiParam(value = "The modified todo."  )  @Valid @RequestBody TodoBase body);
}
