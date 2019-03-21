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

          <!-- Limit to do items in list -->
          <number-input v-model="itemLimit" v-on:change="limitItems" :min="1" :max="100"
                        inline controls rounded class="todo-filter-config-entry"></number-input>

          <b-form-radio-group v-model="sortOption" :options="options" v-on:change="changeSortOption">
          </b-form-radio-group>

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
        sortOption: "state",
        options: [
          {text: "Sort by date", value: "state"},
          {text: "Sort by state", value: "date"}
        ],
        itemLimit: 5,
        sortByDate: true,
        sortByState: false
      }
    },
    methods: {
      changeSortOption() {

        eventBus.$emit("sortingChanged", this.sortOption);
      },
      sortByStateSelected() {

        eventBus.$emit("sortByStateSelected");
      },
      limitItems() {

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
    margin-right: 25px;
  }
</style>
