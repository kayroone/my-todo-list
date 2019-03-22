<template>
  <div class="container-fluid">
    <div class="wrapper-main">

      <!-- To do modify modal -->
      <todo-modify-modal></todo-modify-modal>

      <!-- To do filter config -->
      <div v-if="todos.length > 0">
        <transition-group name="fade">
          <todo-filter-config :key="todoFilterConfigKey"></todo-filter-config>
        </transition-group>
      </div>

      <!-- Handling if no items available -->
      <div v-else>
        <h3 class="todo-no-items-transparency">Do you have nothing to do?</h3>
      </div>

      <!-- Item list -->
      <transition-group name="fade">
        <b-list-group v-for="(todo, idx) in todos" :key="todo.id">
          <b-list-group-item class="list-group-item">

            <!-- Title and date -->
            <template v-if="todo.title.length > 15">
              {{ todo.title.substring(0,15) + ".." }}
            </template>

            <template v-else>
              {{ todo.title }}
            </template>

            | {{ todo.dueDate }}

            <!-- Set done -->
            <label class="checkbox">
              <input type="checkbox" :checked="todo.done" @change="updateTodo(todo, $event)"/>
              <span class="default"></span>
            </label>

            <!-- Modify and delete -->
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
  import {EventBus} from '../../main';
  import {TodoService} from '../../services'
  import TodoFilterConfig from "../ui/TodoFilterConfig";
  import TodoModifyModal from "../dialog/TodoModifyModal";

  export default {
    name: "TodoList",
    components: {TodoModifyModal, TodoFilterConfig},
    data() {
      return {
        itemLimit: 5,
        sortOption: "date",
        todoFilterConfigKey: 0,
        modalShow: false,
        todos: []
      }
    },
    created() {

      /* Load all to do items in vue created state */
      this.loadTodos();

      /* Bind todoAdded event */
      EventBus.$on("todoAdded", newTodo => {
        this.onTodoListAdd(newTodo);
      });

      /* Bind todoModified event */
      EventBus.$on("todoModified", () => {
        this.onTodoListModify();
      });

      /* Bind limitItemsTriggered event */
      EventBus.$on("limitItemsTriggered", (newItemLimit) => {
        this.onTodoListLimitItems(newItemLimit);
      });

      /* Bind sortingChanged event */
      EventBus.$on("sortingChanged", (sortOption) => {
        this.sortingChanged(sortOption);
      });
    },
    beforeDestroy() {

      /* Unbind todoAdded event */
      EventBus.$off("todoAdded", this.onTodoListAdd);

      /* Unbind todoModified event */
      EventBus.$off("todoModified", this.onTodoListModify);

      /* Unbind limitItemsTriggered event */
      EventBus.$off("limitItemsTriggered", this.onTodoListLimitItems);

      /* Unbind sortingChanged event */
      EventBus.$off("sortingChanged", this.sortingChanged);
    },
    methods: {

      /**
       * Load all to do items from server, replace scope to do array and sort array
       * based on chosen sort option.
       *
       * @param state The state (all/unfinished) of the items being fetched.
       * @param limit The maximum of the items being displayed.
       * @param offset From which item to be displayed.
       */

      loadTodos(state, limit, offset) {

        /* Sanity check */
        if (!state) {
          state = "all";
        }
        if (!limit) {
          limit = 5;
        }
        if (!offset) {
          offset = 0;
        }

        TodoService.getTodos(state, limit, offset).then(data => {
          if (data) {
            this.todos = data.slice(0);
            this.sortingChanged(this.sortOption);
          }
        });
      },

      /**
       * Update an to do item on the server.
       *
       * @param todo The to do item to be updated.
       * @param event The click event -> checkbox for finishing a to do item.
       */

      updateTodo(todo, event) {

        todo.done = event.target.checked;
        TodoService.updateTodo(todo);
      },

      /**
       * Delete an to do item on the server and update the local scope to do array.
       *
       * @param todo The to do item to be deleted.
       * @param idx The local scope arrays index of the item.
       */

      deleteTodo(todo, idx) {

        TodoService.deleteTodo(todo.id).then(() => {

          this.todos.splice(idx, 1);

          /* Reload list with active filter config  */
          if (this.todos.length !== this.itemLimit) {
            this.loadTodos(null, this.itemLimit, null);
          }
        });
      },

      /**
       * Open the modify modal and fire modifyModalOpened event.
       *
       * @param todo The to do item to be modified.
       */

      openModifyModal(todo) {

        TodoService.getTodo(todo.id).then((modifyTodo) => {
          EventBus.$emit("modifyModalOpened", modifyTodo)
        });
      },

      /**
       * Add a new to oo item to the local scope array of todos.
       *
       * @param newTodo The new to do item.
       */

      onTodoListAdd(newTodo) {

        this.todos.unshift(newTodo);
      },

      /**
       * Reload the to do list.
       */

      onTodoListModify() {

        this.loadTodos();
      },

      /**
       * Sort the local scope array of todos by date.
       */

      onTodoListSortByDate() {

        /* First sort todos array by ID */
        this.todos.sort(function (todoOne, todoTwo) {
          return todoOne.id - todoTwo.id
        });

        /* Now sort by date */
        this.todos.sort(function (todoOne, todoTwo) {

          const todoOneDate = new Date(todoOne.dueDate);
          const todoTwoDate = new Date(todoTwo.dueDate);

          return todoOneDate - todoTwoDate;
        });
      },

      /**
       * Sort the local scope array of todos by state.
       */

      onTodoListSortByState() {

        /* First sort todos array by ID */
        this.todos.sort(function (todoOne, todoTwo) {
          return todoOne.id - todoTwo.id
        });

        /* Now sort by state */
        this.todos.sort(function (todoOne, todoTwo) {
          return todoOne.done - todoTwo.done
        });
      },

      /**
       * Change sorting option on todos list -> Triggered from TodoFilterConfig.
       *
       * @param sortOption The new sorting option (state/date).
       */

      sortingChanged(sortOption) {

        /* Sanity check */
        if (!sortOption) {
          this.sortOption = "date";
        }

        if (sortOption === "state") {
          this.onTodoListSortByState();
          this.sortOption = "state";
        } else {
          this.onTodoListSortByDate();
          this.sortOption = "date";
        }
      },

      /**
       * Limit the to do items to be displayed.
       *
       * @param newItemLimit The new maximum items to be displayed.
       */

      onTodoListLimitItems(newItemLimit) {

        this.itemLimit = newItemLimit;
        this.loadTodos("all", newItemLimit, 0);
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
