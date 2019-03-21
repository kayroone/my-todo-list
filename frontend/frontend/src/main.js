// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import BootstrapVue from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import {library} from '@fortawesome/fontawesome-svg-core'
import {faCalendar, faCogs, faFileWord, faPen, faTrash} from '@fortawesome/free-solid-svg-icons'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'

import VueNumberInput from '@chenfengyuan/vue-number-input';

Vue.use(VueNumberInput);
Vue.use(BootstrapVue);

library.add(faTrash, faPen, faFileWord, faCalendar, faCogs);
Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.config.productionTip = false;

/* Event bus */
export const EventBus = new Vue();

/* eslint-disable no-new */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>'
});
