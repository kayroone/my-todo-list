package de.jwiegmann.repository;

import de.jwiegmann.model.TodoFull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosRepository extends CrudRepository<TodoFull, Integer> {

    Page<TodoFull> findAll(Pageable pageable);
}
