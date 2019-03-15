package de.jwiegmann.service;

import de.jwiegmann.model.TodoBase;
import de.jwiegmann.model.TodoFull;
import de.jwiegmann.model.TodoList;
import de.jwiegmann.repository.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodosService {

    @Autowired
    TodosRepository todosRepository;

    public Optional<TodoFull> createTodo(TodoBase todoBase) {

        TodoFull todoFull = new TodoFull(todoBase);
        return Optional.of(this.todosRepository.save(todoFull));
    }

    public boolean deleteTodo(Integer todoId) {

        Optional<TodoFull> existingTodo = this.todosRepository.findById(todoId);

        if (existingTodo.isPresent()) {
            this.todosRepository.delete(existingTodo.get());
            return true;
        } else {
            return false;
        }
    }

    public Optional<TodoFull> getTodo(Integer todoId) {

        return this.todosRepository.findById(todoId);
    }

    public Page<TodoFull> getTodos(String state, Integer limit, Integer offset) {

        if (state.equals("unfinished")) {
            state = "done";
        } else {
            state = null;
        }

        Pageable pageable = new PageRequest(offset, limit, Sort.Direction.ASC, state);
        return this.todosRepository.findAll(pageable);
    }

    public Optional<TodoFull> updateTodo(Integer todoId, TodoBase todoBase) {

        Optional<TodoFull> existingTodo = this.todosRepository.findById(todoId);

        if (existingTodo.isPresent()) {

            TodoFull todoFull = existingTodo.get();

            todoFull.setDescription(todoBase.getDescription());
            todoFull.setDueDate(todoBase.getDueDate());
            todoFull.setTitle(todoBase.getTitle());
            todoFull.setDone(todoBase.isDone());

            return Optional.of(this.todosRepository.save(todoFull));
        } else {
            return Optional.empty();
        }
    }
}