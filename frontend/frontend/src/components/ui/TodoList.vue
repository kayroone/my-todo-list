<template>
  <div class="container-fluid">
    <div class="wrapper-main">

      <!--<b-list-group v-for="todo in todos"></b-list-group>-->
      {{ todos }}

    </div>
  </div>
</template>

<script>
  import {todoService} from '../../services'
  import {eventBus} from '../../main';

  export default {
    name: "TodoList",

    data() {
      return {
        todos: []
      }
    },

    created() {
      this.loadTodos();
    },

    mounted() {

      eventBus.$on("todoAdded", function (data) {
        this.todos.push(data);
      }.bind(this));
    },

    methods: {
      loadTodos() {

        todoService.getTodos("all", 5, 0).then(data => {
          this.todos = data;
        });
      }


      /*computed: {

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
        });
      }
    },

    created() {
      // Using the event bus
      eventBus.$on('todo', (data) => {

        console.log(data);

        //this.todos.push(data);
      });
    },

    mixins: [
      confirm
    ],
    mounted() {
      this.loadTodos();
    },
    props: {
      action: {
        required: true,
        type: String
      }
    }*/
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>


</style>
