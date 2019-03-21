// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
/* Bootstrap for vue */
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
/* Font awesome and icons */
import {library} from '@fortawesome/fontawesome-svg-core'
import {faCalendar, faCogs, faFileWord, faPen, faTrash} from '@fortawesome/free-solid-svg-icons'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
/* Number input for limiting number input fields */
import VueNumberInput from '@chenfengyuan/vue-number-input';

Vue.use(VueNumberInput);
Vue.use(BootstrapVue);

/* Adding icons to scope */
library.add(faTrash, faPen, faFileWord, faCalendar, faCogs);
Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.config.productionTip = false;

/* Small event bus */
export const EventBus = new Vue();

/* eslint-disable no-new */
new Vue({
  el: '#app',
  components: {App},
  template: '<App/>'
});
