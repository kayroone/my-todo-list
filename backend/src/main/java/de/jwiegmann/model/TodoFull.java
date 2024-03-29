package de.jwiegmann.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The full todo with identifier used as response object.
 */
@ApiModel(description = "The full todo with identifier used as response object.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-15T21:20:24.293Z")

@Entity
@Table(name = "todos")
public class TodoFull extends TodoBase {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id = null;

    public TodoFull() {
    }

    /**
     * Copy constructor: TodoBase to TodoFull.
     *
     * @param id
     *
     * @return
     */

    public TodoFull(TodoBase todoBase) {

        this.setDescription(todoBase.getDescription());
        this.setDueDate(todoBase.getDueDate());
        this.setTitle(todoBase.getTitle());
        this.setDone(todoBase.isDone());
    }

    /**
     * Copy constructor: TodoList to TodoFull.
     *
     * @param id
     *
     * @return
     */

    public TodoFull(TodoList todoList) {

        this.setDueDate(todoList.getDueDate());
        this.setTitle(todoList.getTitle());
        this.setDone(todoList.isDone());
    }

    public TodoFull id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * minimum: 0
     * maximum: 1000000
     *
     * @return id
     **/
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Min(0)
    @Max(1000000)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TodoFull todoFull = (TodoFull) o;
        return Objects.equals(this.id, todoFull.id) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TodoFull {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

