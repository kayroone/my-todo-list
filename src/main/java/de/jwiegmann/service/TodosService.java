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

/**
 * Main To Do service. Provides To Do item CRUD operations.
 */

@Service
public class TodosService {

    private final static String TODO_IDENTIFIER = "id";
    private final static String TODO_STATE_ALL = "all";
    private final static String TODO_STATE_UNFINISHED = "unfinished";

    @Autowired
    TodosRepository todosRepository;

    /**
     * Create and persist a new To Do item.
     *
     * @param todoBase The To Do item.
     *
     * @return Optional containing the To Do item if store was successful.
     */

    public Optional<TodoFull> createTodo(TodoBase todoBase) {

        TodoFull todoFull = new TodoFull(todoBase);
        return Optional.of(this.todosRepository.save(todoFull));
    }

    /**
     * Delete and persist existing To Do item.
     *
     * @param todoId The To Do item ID.
     *
     * @return true if the To Do item was deleted successful or false if not.
     */

    public boolean deleteTodo(Integer todoId) {

        Optional<TodoFull> existingTodo = this.todosRepository.findById(todoId);

        if (existingTodo.isPresent()) {
            this.todosRepository.delete(existingTodo.get());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get a single To Do item by it's ID.
     *
     * @param todoId The To Do item ID.
     *
     * @return Optional containing the To Do item if the item could be found.
     */

    public Optional<TodoFull> getTodo(Integer todoId) {

        return this.todosRepository.findById(todoId);
    }

    /**
     * Get all To Do items sorted by their ID (ASC) as pageable.
     *
     * @param state  State of the returned To Do items (finished, unfinished, all).
     * @param limit  Maximum To Do items per page.
     * @param offset Define the number from which elements are to be returned.
     *
     * @return Page object containing the sorted and filtered To Do items.
     */

    public Page<TodoFull> getTodos(String state, Integer limit, Integer offset) {

        Pageable pageable = new TodosPageRequest(offset, limit, Sort.DEFAULT_DIRECTION, TODO_IDENTIFIER);

        if (state.equalsIgnoreCase(TODO_STATE_ALL)) {

            return this.todosRepository.findAll(pageable);
        } else if (state.equalsIgnoreCase(TODO_STATE_UNFINISHED)) {

            return this.todosRepository.findAllByDone(false, pageable);
        } else {

            return this.todosRepository.findAllByDone(true, pageable);
        }
    }

    /**
     * Update an existing To Do item.
     *
     * @param todoId   The To Do item ID.
     * @param todoBase The To Do item holding the changes.
     *
     * @return Optional with updated To Do item if update was successful.
     */

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