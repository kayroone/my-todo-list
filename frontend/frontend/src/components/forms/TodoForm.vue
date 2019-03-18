<template>
  <div>
    <b-form @submit.prevent="createTodo">
      <b-form-group id="todo-inputs" v-model="form.todo">

        <!-- Title -->
        <b-form-input id="todo-title" type="text"
                      v-model="form.title" required
                      placeholder="Enter title"/>

        <!-- Description -->
        <b-form-textarea id="todo-description"
                         v-model="form.description"
                         placeholder="Enter description"/>

        <!-- Date -->
        <todo-date-picker id="todo-date" v-model="form.date" required></todo-date-picker>

      </b-form-group>

      <!-- Submit and add -->
      <b-button id="todo-add" type="submit" variant="success">Add</b-button>

    </b-form>
  </div>
</template>

<script>
  import TodoDatePicker from "../ui/TodoDatePicker";

  export default {
    name: 'TodoForm',
    components: {TodoDatePicker},
    data() {
      return {
        form: {
          title: '',
          description: '',
          date: null,
        }
      }
    },
    methods: {
      createTodo() {
        console.dir(this.form.title);
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
