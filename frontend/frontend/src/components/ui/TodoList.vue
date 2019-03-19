<template>
  <div class="container-fluid">
    <div class="wrapper-main">

      <b-list-group v-for="todo in todos" :key="todo.id">

        <div v-if="todo.id != null">
          <b-list-group-item>
            {{ todo.title }} | {{ customFormatter(todo) }}
            <label class="checkbox">
              <input type="checkbox" :checked="todo.done" @change="setDone(todo, $event)"/>
              <span class="default"></span>
            </label>

            <div class="fa-pull-right vertical-center">
              <button class="btn btn-xs pull-right">
                <font-awesome-icon icon="pen"/>
              </button>
              <button class="btn btn-xs pull-right">
                <font-awesome-icon icon="trash"/>
              </button>
            </div>

          </b-list-group-item>
        </div>

      </b-list-group>

    </div>
  </div>
</template>

<script>
  import {todoService} from '../../services'
  import {eventBus} from '../../main';
  import {util} from '../../util/helpers';

  export default {
    name: "TodoList",

    data() {
      return {
        todos: []
      }
    },

    created() {

      this.loadTodos();

      eventBus.$on("todoAdded", newTodo => {
        this.onTodoListUpdate(newTodo);
      });
    },

    beforeDestroy() {

      eventBus.$off("todoAdded", this.onTodoListUpdate)
    },

    methods: {
      loadTodos() {

        todoService.getTodos("all", 5, 0).then(data => {
          this.todos = data.slice(0);
        });
      },

      onTodoListUpdate(newTodo) {

        this.todos.unshift(newTodo);
      },

      setDone(todo, event) {

        todo.done = event.target.checked;
        todoService.updateTodo(todo);
      },

      customFormatter(date) {

        return util.formatDateShort(date);
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  .vertical-center {
    margin-top: 12px;
    top: 50%;
    transform: translateY(-50%);
  }

  @keyframes check {
    0% {
      height: 0;
      width: 0;
    }
    25% {
      height: 0;
      width: 10px;
    }
    50% {
      height: 20px;
      width: 10px;
    }
  }

  .list-group-item {
    margin-top: 7px;
  }

  .checkbox {
    background-color: #fff;
    display: inline-block;
    height: 28px;
    margin: 0 .25em;
    width: 28px;
    border-radius: 4px;
    border: 1px solid #ccc;
    float: left
  }

  .checkbox span {
    display: block;
    height: 28px;
    position: relative;
    width: 28px;
    padding: 0
  }

  .checkbox span:after {
    -moz-transform: scaleX(-1) rotate(135deg);
    -ms-transform: scaleX(-1) rotate(135deg);
    -webkit-transform: scaleX(-1) rotate(135deg);
    transform: scaleX(-1) rotate(135deg);
    -moz-transform-origin: left top;
    -ms-transform-origin: left top;
    -webkit-transform-origin: left top;
    transform-origin: left top;
    border-right: 4px solid #fff;
    border-top: 4px solid #fff;
    content: '';
    display: block;
    height: 20px;
    left: 3px;
    position: absolute;
    top: 15px;
    width: 10px
  }

  .checkbox span:hover:after {
    border-color: #999
  }

  .checkbox input {
    display: none
  }

  .checkbox input:checked + span:after {
    -webkit-animation: check .8s;
    -moz-animation: check .8s;
    -o-animation: check .8s;
    animation: check .8s;
    border-color: #555
  }

  .checkbox input:checked + .default:after {
    border-color: #444
  }

</style>
