package io.swagger.api;

import io.swagger.model.ErrorResponse;
import io.swagger.model.TodoBase;
import io.swagger.model.TodoFull;
import io.swagger.model.TodoList;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-14T08:55:59.545Z")

@Controller
public class TodosApiController implements TodosApi {

    private static final Logger log = LoggerFactory.getLogger(TodosApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TodosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<TodoFull> createTodo(@ApiParam(value = "The new todo."  )  @Valid @RequestBody TodoBase body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<TodoFull>(objectMapper.readValue("{  \"id\" : 1,  \"title\" : \"clean fridge\",  \"description\" : \"It's a mess\",  \"dueDate\" : \"2018-08-27T12:34:56.789Z\",  \"done\" : false}", TodoFull.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<TodoFull>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<TodoFull>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteTodo(@ApiParam(value = "The todo identifier.",required=true) @PathVariable("todo-id") Integer todoId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<TodoFull> getTodo(@ApiParam(value = "The todo identifier.",required=true) @PathVariable("todo-id") Integer todoId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<TodoFull>(objectMapper.readValue("{  \"id\" : 1,  \"title\" : \"clean fridge\",  \"description\" : \"It's a mess\",  \"dueDate\" : \"2018-08-27T12:34:56.789Z\",  \"done\" : false}", TodoFull.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<TodoFull>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<TodoFull>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TodoList>> getTodos(@ApiParam(value = "Filters all or unfinished todos in the response", allowableValues = "all, unfinished", defaultValue = "unfinished") @Valid @RequestParam(value = "state", required = false, defaultValue="unfinished") String state,@Min(0) @Max(10) @ApiParam(value = "Maximal number of todos in the response", defaultValue = "5") @Valid @RequestParam(value = "limit", required = false, defaultValue="5") Integer limit,@Min(0) @Max(100) @ApiParam(value = "Offset for the todos in the response") @Valid @RequestParam(value = "offset", required = false) Integer offset) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<TodoList>>(objectMapper.readValue("[ {  \"id\" : 1,  \"title\" : \"clean fridge\",  \"dueDate\" : \"2018-08-27T12:34:56.789Z\",  \"done\" : false}, {  \"id\" : 2,  \"title\" : \"clean bathrom\",  \"dueDate\" : \"2018-08-28T09:00:00.000Z\",  \"done\" : false}, {  \"id\" : 3,  \"title\" : \"bring out garbage\",  \"dueDate\" : \"2018-08-29T11:00:00.000Z\",  \"done\" : false}, {  \"id\" : 4,  \"title\" : \"go to supermarket\",  \"dueDate\" : \"2018-08-25T14:30:00.000Z\",  \"done\" : true}, {  \"id\" : 5,  \"title\" : \"write user stories\",  \"dueDate\" : \"2018-09-01T10:00:00.000Z\",  \"done\" : false}, {  \"id\" : 6,  \"title\" : \"pay bills\",  \"dueDate\" : \"2018-09-01T16:00:00.000Z\",  \"done\" : false}, {  \"id\" : 7,  \"title\" : \"call mum\",  \"dueDate\" : \"2018-09-01T19:00:00.000Z\",  \"done\" : false} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<TodoList>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<TodoList>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updateTodo(@ApiParam(value = "The todo identifier.",required=true) @PathVariable("todo-id") Integer todoId,@ApiParam(value = "The modified todo."  )  @Valid @RequestBody TodoBase body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
