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
        <todo-date-picker :bootstrap-styling="true" v-model="todo.date"></todo-date-picker>

      </b-form-group>

      <!-- Submit and add -->
      <b-button id="todo-add" type="submit" variant="success">Add</b-button>

    </b-form>
  </div>
</template>

<script>
  import TodoDatePicker from 'vuejs-datepicker';
  import {eventBus} from '../../main';
  import {todoService} from "../../services/todo-service";

  export default {
    name: 'TodoForm',
    components: { todoDatePicker: TodoDatePicker },
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

        console.dir(this.todo.date);

        const newTodo = {
          title: this.todo.title,
          description: this.todo.description,
          date: this.todo.date,
          done: false
        };

        /* Create and get new to do item from API */
        todoService.createTodo(newTodo)
          .then(function (data) {
            eventBus.$emit('todo', data);
          });
      }
    }
  }
</script>

<style scoped>

  #todo-add {
    width: 300px;
  }

  #todo-inputs * {
    margin-top: 10px;
  }

</style>
