<template>
  <div>

    <div class="todo-filter-config-toggle">
      <b-button v-b-toggle.config variant="outline-dark">
        <font-awesome-icon icon="cogs"/>
        Filter config
      </b-button>
    </div>

    <div class="todo-filter-config-entries">
      <b-collapse id="config" class="mt-2">

        <div class="form-inline">
          <number-input class="todo-filter-config-entry" v-model="itemLimit" @change="limitItems"
                        :min="1" :max="100" inline controls rounded></number-input>
          <b-form-radio value="date" v-model="sortByDate" @change="sortByDateSelected"
                        name="some-radios" class="todo-filter-config-entry">
            Sort by date
          </b-form-radio>
          <b-form-radio value="state" v-model="sortByState" @click="sortByStateSelected"
                        name="some-radios">
            Sort by state
          </b-form-radio>
        </div>

      </b-collapse>
    </div>

  </div>
</template>

<script>
  import {eventBus} from '../../main';

  export default {
    name: "TodoFilterConfig",
    data() {
      return {
        itemLimit: 5,
        sortByDate: true,
        sortByState: false
      }
    },
    created() {


    },
    methods: {
      sortByDateSelected() {

        this.sortByDate = true;
        this.sortByState = false;

        eventBus.$emit("sortByDateSelected");
      },
      sortByStateSelected() {

        this.sortByState = true;
        this.sortByDate = false;

        eventBus.$emit("sortByStateSelected");
      },
      limitItems() {

        this.sortByDate = true;
        this.sortByState = false;

        const newItemLimit = this.itemLimit;

        eventBus.$emit("limitItemsTriggered", newItemLimit);
      },
      restrictInput() {

      }
    }
  }
</script>

<style scoped>
  .todo-filter-config-toggle {
    text-align: right;
    margin-bottom: 10px;
    margin-top: -20px;
  }

  .todo-filter-config-entries {
    min-width: 200px;
    margin-bottom: 10px;
  }

  .todo-filter-config-entry {
    margin-right: 20px;
  }
</style>
