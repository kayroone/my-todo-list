<template>
  <div>
    <b-form @submit.prevent="createTodo">
      <b-form-group id="todo-inputs" v-model="todo">

        <!-- Title -->
        <b-form-input id="todo-title" type="text"
                      v-model="todo.title" required
                      placeholder="Enter title"/>

        <!-- Description -->
        <b-form-textarea id="todo-description"
                         v-model="todo.description"
                         placeholder="Enter description"/>

        <!-- Date -->
        <todo-date-picker :bootstrap-styling="true" :format="customFormatter"
                          :value="todo.dueDate"
                          v-model="todo.dueDate">
        </todo-date-picker>

      </b-form-group>

      <!-- Submit and add -->
      <b-button id="todo-add" type="submit" variant="secondary">Add</b-button>

    </b-form>
  </div>
</template>

<script>
  import {EventBus} from '../../main';
  import {DateUtil} from '../../util/date-formatter';
  import {TodoService} from "../../services/todo-service";
  import TodoDatePicker from 'vuejs-datepicker';

  export default {
    name: 'TodoForm',
    components: {todoDatePicker: TodoDatePicker},
    data() {
      return {
        todo: {
          title: '',
          description: '',
          dueDate: new Date(),
          done: false
        }
      }
    },
    methods: {
      createTodo() {

        const newTodo = {
          title: this.todo.title,
          description: this.todo.description,
          dueDate: this.todo.dueDate,
          done: false
        };

        /* Create and get new to do item from API */
        TodoService.createTodo(newTodo)
          .then(function (data) {
            if (data) {
              EventBus.$emit("todoAdded", data);
            }
          });

        /* Clear */
        this.todo.title = "";
        this.todo.description = "";
        this.todo.dueDate = new Date();
      },

      customFormatter(date) {

        return DateUtil.formatDateShort(date);
      }
    }
  }
</script>

<style scoped>
  #todo-add {
    width: 100%;
    min-width: 500px;
  }

  #todo-inputs * {
    margin-top: 10px;
  }

</style>
