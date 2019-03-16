package de.jwiegmann.service;

import de.jwiegmann.model.TodoBase;
import de.jwiegmann.model.TodoFull;
import de.jwiegmann.model.TodosPageRequest;
import de.jwiegmann.repository.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

        Pageable pageable = new TodosPageRequest(offset, limit, Sort.DEFAULT_DIRECTION, "id");

        if (state.equalsIgnoreCase("all")) {
            return this.todosRepository.findAll(pageable);
        } else if (state.equalsIgnoreCase("unfinished")) {
            return this.todosRepository.findAllByDone(false, pageable);
        } else {
            return this.todosRepository.findAllByDone(true, pageable);
        }
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