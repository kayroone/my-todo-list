<template>
    <div class="container-fluid">
      <div class="wrapper-main">

        <p v-if="loading"></p>
        <div v-else>
          <ul class="list-inline">
            <li class="list-inline-item" v-for="(todo, index) in todosSortedByDate" v-bind:key="todo.id">
              <button type="button" class="btn btn-outline-danger" v-on:click.prevent="removeTodo(todo, index)">
                <span class="btn-text">{{ todo.id }}</span>
                <span class="btn-icon" aria-hidden="true">&times;</span>
              </button>
            </li>
          </ul>
          <div class="form-group">
            <div class="input-group">
              <input type="text" class="form-control" autocomplete="name" required ref="name" v-bind:disabled="busy"
                     v-model="name" v-on:keydown.enter.prevent="addTodo"/>
              <div class="input-group-btn">
                <button type="button" class="btn btn-primary" v-on:click.prevent="addTodo" v-bind:disabled="busy">
                  <span class="btn-text" v-html="submitText"/>
                </button>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
</template>

<script>
  import {todoService} from '../../services'
  import {eventBus} from '../../main';

  export default {
    name:"TodoList",
    computed: {

      submitText() {

        return this.busy ? 'Submitting&hellip;' : 'Add todo';
      },

      todosSortedByDate() {

        return this.todos.sort((a, b) => {
          return a.id.localeCompare(b.id);
        });
      }
    },

    data() {
      return {
        busy: false,
        loading: true,
        name: '',
        todos: []
      };
    },

    methods: {

      addTodo() {
        this.busy = true;

        todoService.createTodo().then(() => {

          this.busy = false;
          this.name = '';
          this.$refs.name.focus();
        });
      },

      loadTodos() {

        todoService.getTodos("all", 5, 0).then(data => {

          this.todos = data;
          this.loading = false;
        });
      },

      removeTodo(todo, index) {

        const message = `Are you sure you wish to remove ${todo.id} from this list?`;
        console.log(message);

        /*this.confirm(message, () => {

          todoService.deleteTodo(todo.id)
            .then(() => {
              this.todos.splice(index, 1);
            });
        });*/
      }
    },

    created() {
      // Using the event bus
      eventBus.$on('todo', (data) => {

        console.log(data);

        //this.todos.push(data);
      });
    },

    /*mixins: [
      confirm
    ],*/
    mounted() {
      this.loadTodos();
    },
    props: {
      action: {
        required: true,
        type: String
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>



</style>
