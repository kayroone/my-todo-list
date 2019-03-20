<template>
  <div>

    <div class="todo-filter-config-toggle">
      <v-button v-b-toggle.collapse1 class="btn btn-xs btn-outline-dark">
        <font-awesome-icon icon="cogs"/>
        Filter config
      </v-button>
    </div>

    <div class="todo-filter-config-entries">
      <b-collapse id="collapse1" class="mt-2">

        <div class="form-inline">
          <b-form-input v-model="text1" type="text" class="todo-filter-config-entry" placeholder="Enter item limit"/>
          <b-form-radio value="A" v-model="selected" name="some-radios" class="todo-filter-config-entry">
            Filter by date
          </b-form-radio>
          <b-form-radio value="B" v-model="selected" name="some-radios">
            Filter by state
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
