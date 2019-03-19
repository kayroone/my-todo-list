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
                          v-model="todo.date"></todo-date-picker>

      </b-form-group>

      <!-- Submit and add -->
      <b-button id="todo-add" type="submit" variant="secondary">Add</b-button>

    </b-form>
  </div>
</template>

<script>
  import TodoDatePicker from 'vuejs-datepicker';
  import {todoService} from "../../services/todo-service";
  import {eventBus} from '../../main';
  import {util} from '../../util/helpers';

  export default {
    name: 'TodoForm',
    components: {todoDatePicker: TodoDatePicker},
    data() {
      return {
        todo: {
          title: '',
          description: '',
          date: new Date(),
        }
      }
    },
    methods: {
      createTodo() {

        const newTodo = {
          title: this.todo.title,
          description: this.todo.description,
          dueDate: this.todo.date.toISOString(),
          done: false
        };

        /* Create and get new to do item from API */
        todoService.createTodo(newTodo)
          .then(function (data) {
            eventBus.$emit("todoAdded", data);
          });

        /* Clear */
        this.todo.title = "";
        this.todo.description = "";
        this.todo.date = new Date();
      },

      customFormatter(date) {
        return util.formatDate(date);
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
