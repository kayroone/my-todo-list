package de.jwiegmann.repository;

import de.jwiegmann.model.TodoFull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Main To Do Repository. Provides paging, sorting and CRUD operations.
 */

@Repository
public interface TodosRepository extends PagingAndSortingRepository<TodoFull, Integer> {

    Page<TodoFull> findAll(Pageable pageable);

    Page<TodoFull> findAllByDone(boolean done, Pageable pageable);
}