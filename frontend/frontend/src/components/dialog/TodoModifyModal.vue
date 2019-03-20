<template>
  <div>
    <b-modal ref="todoModifyModalRef" hide-footer :title="'Modify ' + todo.title" @click.native.stop="clearModifyModal">
      <div class="d-block text-center">
        <b-form-group id="todo-inputs" v-model="modified">

          <!-- Title -->
          <b-form-input id="todo-title" type="text"
                        v-model="modified.title" required
                        :placeholder="todo.title"/>

          <!-- Description -->
          <b-form-textarea id="todo-description"
                           v-model="modified.description"
                           :value="todo.description"/>

          <!-- Date -->
          <todo-date-picker :bootstrap-styling="true" :format="customFormatter"
                            :value="todo.dueDate" v-model="modified.dueDate">
          </todo-date-picker>

        </b-form-group>
      </div>

      <b-button class="mt-2" variant="outline-dark"
                block @click="saveModifyModal(todo, modified)">Save
      </b-button>
      <b-button class="mt-3" variant="outline-dark"
                block @click="clearModifyModal">Close
      </b-button>

    </b-modal>
  </div>
</template>

<script>
  import {eventBus} from '../../main';
  import TodoDatePicker from 'vuejs-datepicker';
  import {util} from '../../util/date-formatter';

  export default {
    name: "TodoModifyModal",
    components: {TodoDatePicker},
    data() {
      return {
        modified: {},
        todo: {
          title: '',
          description: '',
          dueDate: new Date(),
          done: false
        }
      }
    },
    created() {

      eventBus.$on("modifyModalOpened", todo => {
        this.showModal(todo);
      });

      eventBus.$on("modifyModalClosed", () => {
        this.clearModifyModal();
      });

    },
    beforeDestroy() {

      eventBus.$off("modifyModalOpened", this.showModal);
      eventBus.$off("modifyModalClosed", this.clearModifyModal);
    },
    methods: {
      showModal(todo) {

        this.todo = todo;
        this.modified.dueDate = util.toDefaultDate(todo.dueDate);

        this.$refs.todoModifyModalRef.show()
      },
      clearModifyModal() {

        this.todo = {};
        this.modified = {};
        this.$refs.todoModifyModalRef.hide()
      },
      saveModifyModal(todo, modified) {

        if (!modified.hasOwnProperty("title")) {
          modified.title = todo.title;
        }
        if (!modified.hasOwnProperty("description")) {
          modified.description = todo.description;
        }
        if (!modified.hasOwnProperty("dueDate")) {
          modified.dueDate = todo.dueDate;
        }

        modified.done = todo.done;
        modified.id = todo.id;
        modified.idx = todo.idx;

        eventBus.$emit("todoModified", modified);

        this.$refs.todoModifyModalRef.toggle('#toggleBtn')
      },
      customFormatter(date) {

        return util.formatDateShort(date);
      }
    }
  }
</script>

<style>

  #todo-inputs * {
    margin-top: 10px;
  }
</style>
