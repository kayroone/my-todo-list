<template>
  <div class="container-fluid">

    <!-- Header -->
    <todo-header></todo-header>

    <!-- Form -->
    <div class="center-element">
      <todo-form></todo-form>
    </div>

    <hr/>

    <!-- List -->
    <div class="center-element">
      <todo-list></todo-list>
    </div>

    <hr/>

    <!-- Footer -->
    <todo-footer></todo-footer>

  </div>
</template>

<script>
  import TodoHeader from "../layout/TodoHeader";
  import TodoList from "../ui/TodoList";
  import TodoFooter from "../layout/TodoFooter";
  import TodoForm from "../forms/TodoAddForm";

  export default {
    name: "TodoMain",
    components: {TodoForm, TodoFooter, TodoList, TodoHeader}
  }
</script>

<style>
  hr {
    max-width: 600px;
  }

  /* Group and center elements with border */
  .center-element {
    min-width: 500px;
    max-width: 600px;
    min-height: 100px;
    padding: 30px 38px 30px;
    margin: 20px auto 0;
    background-color: #ffffff;
  }

  /* Responsive stuff */
  @media (max-width: 700px) {
    .center-element {
      margin-top: 25px;
      margin-bottom: 0;
    }
  }
</style>
