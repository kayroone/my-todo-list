<template>
  <div>
    <div class="space-top text-center">
      <b-button class="text-dark" variant="link" @click="openImprintModal">Imprint</b-button>
    </div>
    <modal ref="modal"></modal>
  </div>
</template>

<script>
  import {eventBus} from '../../main';
  import modal from '../dialog/TodoImprintModal';

  export default {
    name: "TodoFooter",
    components: {modal},
    methods: {
      openImprintModal() {

        eventBus.$emit("imprintModalOpened");
      }
    }
  }
</script>

<style scoped>
  .space-top {
    margin-top: 10px;
  }
</style>
