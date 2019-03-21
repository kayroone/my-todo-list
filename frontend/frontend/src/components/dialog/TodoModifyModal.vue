<template>
  <div>
    <b-modal ref="todoModifyModalRef" hide-footer title="Modify">
      <div class="d-block text-center">
        <b-form-group v-model="modified">

          <!-- Title -->
          <b-form-input id="todo-title" type="text" class="todo-modify-input"
                        v-model="modified.title" required
                        :placeholder="modified.title"/>

          <!-- Description -->
          <b-form-textarea id="todo-description" class="todo-modify-input"
                           v-model="modified.description"
                           :value="modified.description"/>

          <!-- Date -->
          <todo-date-picker :bootstrap-styling="true" :format="customFormatter" class="todo-modify-input"
                            :value="modified.dueDate" v-model="modified.dueDate">
          </todo-date-picker>

        </b-form-group>
      </div>

      <!-- Save modified to do item -->
      <b-button class="mt-2" variant="outline-dark"
                block @click="saveModifyModal(modified)">Save
      </b-button>

      <!-- Discard changes and close modal -->
      <b-button class="mt-3" variant="outline-dark"
                block @click="clearModifyModal">Close
      </b-button>

    </b-modal>
  </div>
</template>

<script>
  import {EventBus} from '../../main';
  import {DateUtil} from '../../util';
  import {TodoService} from '../../services'
  import TodoDatePicker from 'vuejs-datepicker';

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
    created() {

      /* Bind modal show event */
      EventBus.$on("modifyModalOpened", todo => {
        this.showModal(todo);
      });

      /* Bind modal close event */
      EventBus.$on("modifyModalClosed", () => {
        this.clearModifyModal();
      });
    },
    beforeDestroy() {

      /* Unbind modal show event */
      EventBus.$off("modifyModalOpened", this.showModal);

      /* Unbind modal close event */
      EventBus.$off("modifyModalClosed", this.clearModifyModal);
    },
    methods: {

      /**
       * Copy to do item to local scope and open the modal.
       *
       * @param todo The to do item that will be displayed in this modal.
       */

      showModal(todo) {

        this.modified.title = todo.title;
        this.modified.description = todo.description;
        this.modified.done = todo.done;
        this.modified.id = todo.id;
        this.modified.dueDate = DateUtil.toDefaultDate(todo.dueDate);

        this.$refs.todoModifyModalRef.show()
      },

      /**
       * Clear local scope.
       */

      clearModifyModal() {

        this.modified = {};
        this.$refs.todoModifyModalRef.hide()
      },

      /**
       * Save changes to server and fire todoModified event.
       *
       * @param modified The object holding the changes.
       */

      saveModifyModal(modified) {

        TodoService.updateTodo(modified).then(() => {
          EventBus.$emit("todoModified");
          this.$refs.todoModifyModalRef.toggle('#toggleBtn')
        });
      },

      /**
       * Custom date formatter for datepicker.
       *
       * @param date
       * @returns {*}
       */

      customFormatter(date) {

        return DateUtil.formatDateShort(date);
      }
    }
  }
</script>

<style scoped>

  .todo-modify-input {
    margin-top: 10px;
  }
</style>
