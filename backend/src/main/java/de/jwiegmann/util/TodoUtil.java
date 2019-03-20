package de.jwiegmann.util;

import de.jwiegmann.model.TodoFull;
import de.jwiegmann.model.TodoList;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class TodoUtil {

    /**
     * Maps a page holding TodoFull objects to a list of TodoList objects.
     *
     * @param todosPage The page object holding the TodoFull objects.
     *
     * @return A list holding the TodoList objects.
     */

    public static List<TodoList> mapFullToList(Page<TodoFull> todosPage) {

        return todosPage.getContent().stream().map(TodoList::new).collect(Collectors.toList());
    }
}
