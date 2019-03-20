<template>
  <div>
    <b-modal ref="todoModifyModalRef" hide-footer :title="'Modify ' + modified.title">
      <div class="d-block text-center">
        <b-form-group id="todo-inputs" v-model="modified">

          <!-- Title -->
          <b-form-input id="todo-title" type="text"
                        v-model="modified.title" required
                        :placeholder="modified.title"/>

          <!-- Description -->
          <b-form-textarea id="todo-description"
                           v-model="modified.description"
                           :value="modified.description"/>

          <!-- Date -->
          <todo-date-picker :bootstrap-styling="true" :format="customFormatter"
                            :value="modified.dueDate" v-model="modified.dueDate">
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
        modified: {
          title: '',
          description: '',
          dueDate: new Date(),
          done: false,
          id: null,
          idx: null
        },
        todo: {
          title: '',
          description: '',
          dueDate: new Date(),
          done: false,
          id: null,
          idx: null
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

        this.modified = {};
        this.todo = todo;

        this.modified.title = todo.title;
        this.modified.description = todo.description ? todo.description : "";
        this.modified.done = todo.done;
        this.modified.id = todo.id;
        this.modified.idx = todo.idx;
        this.modified.dueDate = util.toDefaultDate(todo.dueDate);

        this.$refs.todoModifyModalRef.show()
      },
      clearModifyModal() {

        this.todo = {};
        this.modified = {};

        this.$refs.todoModifyModalRef.hide()
      },
      saveModifyModal(todo, modified) {

        this.modified.dueDate = util.toDefaultDate(modified.dueDate);
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
