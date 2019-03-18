<template>
  <div class="container-fluid">

    <!-- Header -->
    <todo-header></todo-header>

    <!-- Add form -->
    <div class="todo-center-with-border">
      <todo-form></todo-form>
    </div>

    <div class="todo-center-with-border">
      <todo-list></todo-list>
    </div>

    <!-- Footer -->
    <todo-footer></todo-footer>

  </div>
</template>

<script>
  import TodoHeader from "../layout/TodoHeader";
  import TodoList from "../ui/TodoList";
  import TodoFooter from "../layout/TodoFooter";
  import TodoForm from "../forms/TodoForm";

  export default {
    name: "TodoMain",
    components: {TodoForm, TodoFooter, TodoList, TodoHeader}
  }
</script>

<style>

  /* Group and center elements with border */
  .todo-center-with-border {
    min-width: 500px;
    max-width: 600px;
    min-height: 100px;
    padding: 30px 38px 30px;
    margin: 20px auto 0;
    background-color: #ffffff;
    border: 3px dotted rgba(0, 0, 0, 0.1);
  }

  /* Responsive stuff */
  @media (max-width: 700px) {
    .todo-center-with-border {
      margin-top: 25px;
      margin-bottom: 0;
    }
  }

</style>
