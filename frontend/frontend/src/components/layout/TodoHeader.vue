<template>
  <h2>My Todo's</h2>
</template>

<script>
    export default {
        name: "TodoHeader"
    }
</script>

<style scoped>

  @font-face {
    font-family: 'blackjack';
    src: url('../../fonts/blackjack.ttf') format('truetype');
  }

  h2 {
    font-family: 'blackjack', Fallback, sans-serif;
    font-size: 70px;
    color: black;
  }
</style>
