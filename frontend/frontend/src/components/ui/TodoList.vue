<template>
  <div class="container-fluid">
    <div class="wrapper-main">

      <modal ref="modal"></modal>

      <div v-if="todos.length > 0">
        <transition-group name="fade">
          <todo-filter-config :key="todoFilterConfigKey"></todo-filter-config>
        </transition-group>
      </div>

      <div v-else>
        <h3 class="todo-no-items-transparency">Do you have nothing to do?</h3>
      </div>

      <transition-group name="fade">
        <b-list-group v-for="(todo, idx) in todos" :key="todo.id">
          <b-list-group-item>

            {{ todo.title }} | {{ todo.dueDate }}
            <label class="checkbox">
              <input type="checkbox" :checked="todo.done" @change="updateTodo(todo, $event)"/>
              <span class="default"></span>
            </label>

            <div class="fa-pull-right vertical-center">
              <button class="btn btn-xs pull-right" @click="openModifyModal(todo)">
                <font-awesome-icon icon="pen"/>
              </button>
              <button class="btn btn-xs pull-right" @click="deleteTodo(todo, idx)">
                <font-awesome-icon icon="trash"/>
              </button>
            </div>

          </b-list-group-item>
        </b-list-group>
      </transition-group>

    </div>
  </div>
</template>

<script>
  import {todoService} from '../../services'
  import {eventBus} from '../../main';
  import modal from "../dialog/TodoModifyModal";
  import TodoFilterConfig from "../ui/TodoFilterConfig";

  export default {
    name: "TodoList",
    components: {modal, TodoFilterConfig},
    data() {
      return {
        todoFilterConfigKey: 0,
        modalShow: false,
        todos: []
      }
    },
    created() {

      this.loadTodos();

      eventBus.$on("todoAdded", newTodo => {
        this.onTodoListAdd(newTodo);
      });
      eventBus.$on("todoModified", () => {
        this.onTodoListModify();
      });
    },
    beforeDestroy() {

      eventBus.$off("todoAdded", this.onTodoListAdd);
      eventBus.$off("todoModified", this.onTodoListModify);
    },
    methods: {
      loadTodos() {

        todoService.getTodos("all", 5, 0).then(data => {
          if (data) {
            this.todos = data.slice(0);
          }
        });
      },
      updateTodo(todo, event) {

        todo.done = event.target.checked;
        todoService.updateTodo(todo);
      },
      deleteTodo(todo, idx) {

        todoService.deleteTodo(todo.id);
        this.todos.splice(idx, 1);
      },
      openModifyModal(todo) {

        todoService.getTodo(todo.id).then((modifyTodo) => {
          eventBus.$emit("modifyModalOpened", modifyTodo)
        });
      },
      onTodoListAdd(newTodo) {

        this.todos.unshift(newTodo);
      },
      onTodoListModify() {

        this.loadTodos();
      }
    },
  }
</script>

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
    max-height: 52px;
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

  #todo-inputs * {
    margin-top: 10px;
  }

  .fade-enter-active, .fade-leave-active {
    transition: opacity .5s;
  }

  .fade-enter, .fade-leave-to {
    opacity: 0;
  }

  .todo-no-items-transparency {
    opacity: 0.3;
  }
</style>
