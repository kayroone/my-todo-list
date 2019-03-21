<template>
  <div>
    <b-modal ref="todoModifyModalRef" hide-footer title="Modify">
      <div class="d-block text-center">
        <b-form-group v-model="modified">

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
                block @click="saveModifyModal(modified)">Save
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
  import {todoService} from '../../services'

  export default {
    name: "TodoModifyModal",
    components: {TodoDatePicker},
    data() {
      return {
        modified: {
          id: null,
          title: '',
          description: '',
          dueDate: new Date(),
          done: false,
        }
      }
    },
    mounted() {

      eventBus.$on("modifyModalOpened", todo => {
        this.showModal(todo);
      });
    },
    created() {

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

        this.modified.title = todo.title;
        this.modified.description = todo.description;
        this.modified.done = todo.done;
        this.modified.id = todo.id;
        this.modified.dueDate = util.toDefaultDate(todo.dueDate);

        this.$refs.todoModifyModalRef.show()
      },
      clearModifyModal() {

        this.modified = {};
        this.$refs.todoModifyModalRef.hide()
      },
      saveModifyModal(modified) {

        todoService.updateTodo(modified).then(() => {
          eventBus.$emit("todoModified");
          this.$refs.todoModifyModalRef.toggle('#toggleBtn')
        });
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
