package de.jwiegmann.service;

import de.jwiegmann.model.TodoBase;
import de.jwiegmann.model.TodoFull;
import de.jwiegmann.repository.TodosRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;

import static de.jwiegmann.custom.TodoAssert.assertThat;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TodosServiceTest {

    @Mock
    private TodosRepository todosRepository;

    @InjectMocks
    private TodosService todosService;

    @Test
    public void createUserAndCheckServiceResponse() {

        // 1. Arrange
        TodoBase todoBase = new TodoBase();

        todoBase.setDescription("foo");
        todoBase.setDone(false);

        OffsetDateTime dateTime = OffsetDateTime.now(Clock.systemDefaultZone());
        todoBase.setDueDate(dateTime);

        todoBase.setTitle("bar");

        TodoFull todoFull = new TodoFull(todoBase);

        todoFull.setId(1);

        // 2. Action
        when(this.todosRepository.save(todoFull)).then(returnsFirstArg());
        when(this.todosService.createTodo(todoBase)).then(returnsFirstArg());

        Optional<TodoFull> newTodo = todosService.createTodo(todoBase);

        // 3. Assert
        assertTrue(newTodo.isPresent());
        assertThat(newTodo.get()).hasDescription();
        assertThat(newTodo.get()).hasDueDate();
        assertThat(newTodo.get()).hasTitle();
        assertThat(newTodo.get()).hasDoneValue();
    }

}
