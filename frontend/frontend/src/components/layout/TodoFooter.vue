<template>
  <div>

    <!-- Imprint link -->
    <div class="space-top text-center">
      <b-button class="text-dark" variant="link" @click="openImprintModal">Imprint</b-button>
    </div>

    <!-- Imprint modal -->
    <todo-imprint-modal></todo-imprint-modal>
  </div>
</template>

<script>
  import {EventBus} from '../../main';
  import TodoImprintModal from '../dialog/TodoImprintModal';

  export default {
    name: "TodoFooter",
    components: {TodoImprintModal},
    methods: {

      /**
       * Trigger imprintModalOpened event.
       */

      openImprintModal() {

        EventBus.$emit("imprintModalOpened");
      }
    }
  }
</script>

<style scoped>
  .space-top {
    margin-top: 10px;
  }
</style>
