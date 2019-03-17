package de.jwiegmann.custom;

import de.jwiegmann.model.TodoFull;
import org.assertj.core.api.AbstractAssert;

public class TodoAssert extends AbstractAssert<TodoAssert, TodoFull> {

    public TodoAssert(TodoFull todoFull) {

        super(todoFull, TodoAssert.class);
    }

    public static TodoAssert assertThat(TodoFull todoFull) {

        return new TodoAssert(todoFull);
    }

    public TodoAssert hasDescription() {

        isNotNull();
        if (actual.getDescription() == null) {
            failWithMessage("Expected todo to have a description, but it was null");
        }

        return this;
    }

    public TodoAssert hasDueDate() {

        isNotNull();
        if (actual.getDueDate() == null) {
            failWithMessage("Expected todo to have a due date, but it was null");
        }

        return this;
    }

    public TodoAssert hasTitle() {

        isNotNull();
        if (actual.getTitle() == null) {
            failWithMessage("Expected todo to have a title, but it was null");
        }

        return this;
    }

    public TodoAssert hasDoneValue() {

        isNotNull();
        if (actual.isDone() == null) {
            failWithMessage("Expected todo to have a done value, but it was null");
        }

        return this;
    }
}