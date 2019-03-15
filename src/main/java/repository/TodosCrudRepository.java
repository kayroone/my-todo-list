package repository;

import io.swagger.model.TodoFull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosCrudRepository extends CrudRepository<TodoFull, Integer> { }
