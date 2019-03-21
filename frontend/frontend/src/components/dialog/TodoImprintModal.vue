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
  import {EventBus} from '../../main';
  import {FrontendConfig} from '../../config';

  export default {
    name: "TodoImprintModal",
    data() {
      return {
        imprintName: FrontendConfig.imprintName,
        imprintStreet: FrontendConfig.imprintStreet,
        imprintTown: FrontendConfig.imprintTown,
        imprintPostcode: FrontendConfig.imprintPostcode,
        imprintPhone: FrontendConfig.imprintPhone,
        imprintEmail: FrontendConfig.imprintEmail
      }
    },
    created() {

      /* Bind modal show event */
      EventBus.$on("imprintModalOpened", () => {
        this.showModal();
      });
    },
    beforeDestroy() {

      /* Unbind modal show event */
      EventBus.$off("imprintModalOpened", this.showModal);
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
