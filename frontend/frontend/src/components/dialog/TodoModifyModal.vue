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
  import {EventBus} from '../../main';
  import {DateUtil} from '../../util/date-formatter';
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
    mounted() {

      EventBus.$on("modifyModalOpened", todo => {
        this.showModal(todo);
      });
    },
    created() {

      EventBus.$on("modifyModalClosed", () => {
        this.clearModifyModal();
      });
    },
    beforeDestroy() {

      EventBus.$off("modifyModalOpened", this.showModal);
      EventBus.$off("modifyModalClosed", this.clearModifyModal);
    },
    methods: {
      showModal(todo) {

        this.modified.title = todo.title;
        this.modified.description = todo.description;
        this.modified.done = todo.done;
        this.modified.id = todo.id;
        this.modified.dueDate = DateUtil.toDefaultDate(todo.dueDate);

        this.$refs.todoModifyModalRef.show()
      },
      clearModifyModal() {

        this.modified = {};
        this.$refs.todoModifyModalRef.hide()
      },
      saveModifyModal(modified) {

        TodoService.updateTodo(modified).then(() => {
          EventBus.$emit("todoModified");
          this.$refs.todoModifyModalRef.toggle('#toggleBtn')
        });
      },
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
