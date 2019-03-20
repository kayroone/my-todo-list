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
          <b-form-checkbox id="checkbox1" name="checkbox1" class="todo-filter-config-entry" v-model="status"
                           value="accepted" unchecked-value="not_accepted">
            Filter by title
          </b-form-checkbox>
          <b-form-checkbox id="checkbox2" name="checkbox1" v-model="status" value="accepted"
                           unchecked-value="not_accepted">
            Filter by date
          </b-form-checkbox>

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
    margin-bottom: 7px;
  }

  .todo-filter-config-entry {
    margin-right: 20px;
  }
</style>
