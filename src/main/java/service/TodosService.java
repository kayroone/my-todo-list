package service;

import io.swagger.model.TodoBase;
import io.swagger.model.TodoFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TodosRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodosService {

    @Autowired
    TodosRepository todosRepository;

    public Optional<TodoFull> createTodo(TodoBase todoBase) {

        TodoFull todoFull = new TodoFull(todoBase);
        return Optional.of(this.todosRepository.save(todoFull));
    }

    public void deleteTodo(TodoBase todoBase) {

        TodoFull todoFull = new TodoFull(todoBase);
        this.todosRepository.delete(todoFull);
    }

    public Optional<TodoFull> getTodo(TodoFull todoFull) {

        return Optional.of(this.todosRepository.findOne(todoFull.getId()));
    }

    public Optional<List<TodoFull>> getTodos() {

        Iterable<TodoFull> iterable = this.todosRepository.findAll();
        List<TodoFull> todoFullList = new ArrayList<>();
        iterable.forEach(todoFullList::add);

        return Optional.of(todoFullList);
    }

    public Optional<TodoFull> updateTodo(TodoFull todoFull) {

        TodoFull existingTodo = this.todosRepository.findOne(todoFull.getId());

        if (existingTodo != null) {
            return Optional.of(this.todosRepository.save(todoFull));
        } else {
            return Optional.empty();
        }
    }
}