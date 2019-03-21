<template>
  <div>
    <b-modal ref="todoImprintModalRef" hide-footer title="Imprint">
      <div class="imprint-data">
        <b>Angaben gemäß § 5 TMG:</b><br>
        {{ imprintName }} <br>
        {{ imprintStreet }} <br>
        {{ imprintTown }} <br>
        {{ imprintPostcode }} <br>

        <p/>
        <b>Vertreten durch:</b><br>
        {{ imprintName }} <br>

        <p/>
        <b>Kontakt:</b><br>
        Telefon: {{ imprintPhone }} <br>
        E-Mail: {{ imprintEmail }} <br>
        <p/>
      </div>

    </b-modal>
  </div>
</template>

<script>
  import {eventBus} from '../../main';
  import {frontendConfig} from '../../config/frontend-config';

  export default {
    name: "TodoImprintModal",
    data() {
      return {
        imprintName: frontendConfig.imprintName,
        imprintStreet: frontendConfig.imprintStreet,
        imprintTown: frontendConfig.imprintTown,
        imprintPostcode: frontendConfig.imprintPostcode,
        imprintPhone: frontendConfig.imprintPhone,
        imprintEmail: frontendConfig.imprintEmail
      }
    },
    created() {

      eventBus.$on("imprintModalOpened", () => {

        this.showModal();
      });
    },
    beforeDestroy() {

      eventBus.$off("imprintModalOpened", this.showModal);
    },
    methods: {
      showModal() {

        this.$refs.todoImprintModalRef.show()
      },

      closeModal() {

        this.$refs.todoImprintModalRef.hide()
      }
    }
  }
</script>

<style scoped>

  .imprint-data {
    text-align: left;
    float: left;
  }
</style>
