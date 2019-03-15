package service;

import io.swagger.model.TodoBase;
import io.swagger.model.TodoFull;
import io.swagger.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.TodosCrudRepository;
import repository.TodosPaginationAndSortingRepository;

import java.util.Optional;

@Service
public class TodosService {

    @Autowired
    TodosCrudRepository todosRepository;

    @Autowired
    TodosPaginationAndSortingRepository todosPaginationAndSortingRepository;

    public Optional<TodoFull> createTodo(TodoBase todoBase) {

        TodoFull todoFull = new TodoFull(todoBase);
        return Optional.of(this.todosRepository.save(todoFull));
    }

    public boolean deleteTodo(Integer todoId) {

        TodoFull existingTodo = this.todosRepository.findOne(todoId);

        if (existingTodo != null) {
            this.todosRepository.delete(existingTodo);
            return true;
        } else {
            return false;
        }
    }

    public Optional<TodoFull> getTodo(Integer todoId) {

        return Optional.of(this.todosRepository.findOne(todoId));
    }

    public Page<TodoList> getTodos(String state, Integer limit, Integer offset) {

        if (state.equals("unfinished")) {
            state = "done";
        } else {
            state = null;
        }

        Pageable pageable = new PageRequest(offset, limit, Sort.Direction.ASC, state);
        return this.todosPaginationAndSortingRepository.findAll(pageable);
    }

    public Optional<TodoFull> updateTodo(Integer todoId, TodoBase todoBase) {

        TodoFull existingTodo = this.todosRepository.findOne(todoId);

        if (existingTodo != null) {

            existingTodo.setDescription(todoBase.getDescription());
            existingTodo.setDueDate(todoBase.getDueDate());
            existingTodo.setTitle(todoBase.getTitle());
            existingTodo.setDone(todoBase.isDone());

            return Optional.of(this.todosRepository.save(existingTodo));
        } else {
            return Optional.empty();
        }
    }
}