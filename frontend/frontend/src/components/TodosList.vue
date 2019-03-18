<template>
  <div class="container-fluid">
    <div class="wrapper-list-main">

      <h2>Klex</h2>

      <ul id="todo-list">
        <li v-for="todo in todos">
          {{ todo }}
        </li>
      </ul>

    </div>
  </div>
</template>

<script>
  import {todoService} from '../services'

  const todos = todoService.getTodos("all", "5", "0");

  export default {
    data() {
      return {
        todos
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  .wrapper-list-main {
    max-width: 400px;
    max-height: 400px;
    padding: 30px 38px 66px;
    margin: 0 auto;
    background-color: #ffffff;
    border: 3px dotted rgba(0, 0, 0, 0.1);
  }

  @media (max-width: 700px) {
    .wrapper-list-main {
      margin-top: 25px;
      margin-bottom: 0;
    }
  }

  @font-face {
    font-family: 'blackjack';
    src: url('../fonts/blackjack.ttf') format('truetype');
  }

  h2 {
    font-family: 'blackjack', Fallback, sans-serif;
    font-size: 70px;
    color: black;
  }

</style>
