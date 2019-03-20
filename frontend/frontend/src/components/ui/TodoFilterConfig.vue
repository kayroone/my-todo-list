<template>
  <div>

    <div class="todo-filter-config-toggle">
      <b-button v-b-toggle.collapse1 variant="outline-dark">
        <font-awesome-icon icon="cogs"/>
        Filter config
      </b-button>
    </div>

    <div class="todo-filter-config-entries">
      <b-collapse id="collapse1" class="mt-2">

        <div class="form-inline">
          <b-form-input type="number" class="todo-filter-config-entry" placeholder="Enter item limit"/>
          <b-form-radio value="date" name="some-radios" class="todo-filter-config-entry">
            Sort by date
          </b-form-radio>
          <b-form-radio value="state" name="some-radios">
            Sort by state
          </b-form-radio>
        </div>

      </b-collapse>
    </div>

  </div>
</template>

<script>
  export default {
    name: "TodoFilterConfig"
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
