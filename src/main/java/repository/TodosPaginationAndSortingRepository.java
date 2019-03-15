package repository;

import io.swagger.model.TodoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosPaginationAndSortingRepository extends PagingAndSortingRepository<TodoList, Integer> {

    Iterable<TodoList> findAll(Sort sort);
    Page<TodoList> findAll(Pageable pageable);
}
