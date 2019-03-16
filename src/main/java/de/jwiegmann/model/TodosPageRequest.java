package de.jwiegmann.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * Better implementation of PageRequest for setting offset and limit.
 */
public class TodosPageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = -25822477129613575L;
    private final Sort sort;
    private int limit;
    private int offset;

    /**
     * Creates a new {@link TodosPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     * @param sort   can be {@literal null}.
     */
    private TodosPageRequest(int offset, int limit, Sort sort) {

        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }

        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    /**
     * Creates a new {@link TodosPageRequest} with sort parameters applied.
     *
     * @param offset     zero-based offset.
     * @param limit      the size of the elements to be returned.
     * @param direction  the direction of the {@link Sort} to be specified, can be {@literal null}.
     * @param properties the properties to sort by, must not be {@literal null} or empty.
     */
    public TodosPageRequest(int offset, int limit, Sort.Direction direction, String... properties) {

        this(offset, limit, new Sort(direction, properties));
    }

    /**
     * Creates a new {@link TodosPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     */
    public TodosPageRequest(int offset, int limit) {

        this(offset, limit, new Sort(Sort.Direction.ASC, "id"));
    }

    @Override
    public int getPageNumber() {

        return offset / limit;
    }

    @Override
    public int getPageSize() {

        return limit;
    }

    @Override
    public long getOffset() {

        return offset;
    }

    @Override
    public Sort getSort() {

        return sort;
    }

    @Override
    public Pageable next() {

        return new TodosPageRequest((int) (getOffset() + getPageSize()), getPageSize(), getSort());
    }

    private TodosPageRequest previous() {

        return hasPrevious() ? new TodosPageRequest((int) (getOffset() - getPageSize()), getPageSize(), getSort()) : this;
    }

    @Override
    public Pageable previousOrFirst() {

        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {

        return new TodosPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {

        return offset > limit;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof TodosPageRequest)) {
            return false;
        }

        TodosPageRequest that = (TodosPageRequest) o;

        return this.equals(that);
    }

    @Override
    public int hashCode() {

        return 31 * super.hashCode() + sort.hashCode();
    }

    @Override
    public String toString() {

        return "limit: " + limit + " offset: " + offset + " sort: " + sort;
    }
}



