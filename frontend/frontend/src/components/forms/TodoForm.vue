<template>
  <div>
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group id="todo-inputs" v-model="form.todo">

        <b-form-input id="todo-title" type="text"
                      v-model="form.title" required
                      placeholder="Enter title"/>

        <b-form-textarea id="todo-description"
                         v-model="form.description"
                         placeholder="Enter description"/>

        <todo-date-picker id="todo-date"></todo-date-picker>

      </b-form-group>

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
          email: '',
          name: '',
          food: null,
          checked: []
        },
        foods: [{text: 'Select One', value: null}, 'Carrots', 'Beans', 'Tomatoes', 'Corn'],
        show: true
      }
    },
    methods: {
      onSubmit(evt) {

        evt.preventDefault();
        alert(JSON.stringify(this.form))
      },
      onReset(evt) {

        evt.preventDefault();
        /* Reset our form values */
        this.form.email = '';
        this.form.name = '';
        this.form.food = null;
        this.form.checked = [];
        /* Trick to reset/clear native browser form validation state */
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        })
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
