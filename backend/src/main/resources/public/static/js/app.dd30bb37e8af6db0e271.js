webpackJsonp([1],{"9M+g":function(t,e){},Jmt5:function(t,e){},NHnr:function(t,e,o){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=o("7+uW"),r={render:function(){var t=this.$createElement;return(this._self._c||t)("h2",[this._v("My Todo's")])},staticRenderFns:[]};var i=o("VU/8")({name:"TodoHeader"},r,!1,function(t){o("pBZl")},"data-v-511b3d65",null).exports,a=o("//Fk"),s=o.n(a),d=o("mvHQ"),c=o.n(d),u={apiUrl:"http://localhost:9080/todos"},l=o("7t+N"),p=o.n(l),f={createTodo:function(t){var e={method:"POST",headers:{"Content-Type":"application/json"},body:c()(t),mode:"cors"};return fetch(""+u.apiUrl,e).then(m)},deleteTodo:function(t){var e=""+u.apiUrl+t;return fetch(e,{method:"DELETE"}).then(function(t){return t.status})},getTodo:function(t){var e=""+u.apiUrl+t;return fetch(e,{method:"GET"}).then(m).then(function(t){return t})},getTodos:function(t,e,o){var n=u.apiUrl+"?"+p.a.param({state:t,limit:e,offset:o});return fetch(n,{method:"GET",mode:"cors"}).then(m).then(function(t){return t})},updateTodo:function(t){var e={method:"PUT",body:c()(t)};return fetch(""+u.apiUrl,e).then(function(t){return t.status})}};function m(t){return t.text().then(function(e){var o=e&&JSON.parse(e);if(!t.ok){401===t.status&&location.reload(!0);var n=o&&o.message||t.statusText;return s.a.reject(n)}return o})}var v={name:"TodoList",computed:{submitText:function(){return this.busy?"Submitting&hellip;":"Add todo"},todosSortedByDate:function(){return this.todos.sort(function(t,e){return t.id.localeCompare(e.id)})}},data:function(){return{busy:!1,loading:!0,name:"",todos:[]}},methods:{addTodo:function(){var t=this;this.busy=!0,f.createTodo().then(function(){t.busy=!1,t.name="",t.$refs.name.focus()})},loadTodos:function(){var t=this;f.getTodos("all",5,0).then(function(e){t.todos=e,t.loading=!1})},removeTodo:function(t,e){var o="Are you sure you wish to remove "+t.id+" from this list?";console.log(o)}},created:function(){$.$on("todo",function(t){console.log(t)})},mounted:function(){this.loadTodos()},props:{action:{required:!0,type:String}}},h={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",{staticClass:"container-fluid"},[o("div",{staticClass:"wrapper-main"},[t.loading?o("p"):o("div",[o("ul",{staticClass:"list-inline"},t._l(t.todosSortedByDate,function(e,n){return o("li",{key:e.id,staticClass:"list-inline-item"},[o("button",{staticClass:"btn btn-outline-danger",attrs:{type:"button"},on:{click:function(o){return o.preventDefault(),t.removeTodo(e,n)}}},[o("span",{staticClass:"btn-text"},[t._v(t._s(e.id))]),t._v(" "),o("span",{staticClass:"btn-icon",attrs:{"aria-hidden":"true"}},[t._v("×")])])])}),0),t._v(" "),o("div",{staticClass:"form-group"},[o("div",{staticClass:"input-group"},[o("input",{directives:[{name:"model",rawName:"v-model",value:t.name,expression:"name"}],ref:"name",staticClass:"form-control",attrs:{type:"text",autocomplete:"name",required:"",disabled:t.busy},domProps:{value:t.name},on:{keydown:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:(e.preventDefault(),t.addTodo(e))},input:function(e){e.target.composing||(t.name=e.target.value)}}}),t._v(" "),o("div",{staticClass:"input-group-btn"},[o("button",{staticClass:"btn btn-primary",attrs:{type:"button",disabled:t.busy},on:{click:function(e){return e.preventDefault(),t.addTodo(e)}}},[o("span",{staticClass:"btn-text",domProps:{innerHTML:t._s(t.submitText)}})])])])])])])])},staticRenderFns:[]};var b=o("VU/8")(v,h,!1,function(t){o("mrZN")},"data-v-d1b6a64c",null).exports,T={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"space-top text-center"},[e("b-button",{staticClass:"text-dark",attrs:{variant:"link"}},[this._v("Imprint")])],1)},staticRenderFns:[]};var _=o("VU/8")({name:"TodoFooter"},T,!1,function(t){o("WwAX")},"data-v-7db8ba66",null).exports,y={name:"TodoForm",components:{TodoDatePicker:TodoDatePicker},data:function(){return{todo:{title:"",description:"",date:new Date}}},methods:{createTodo:function(){console.dir(this.todo.date);var t={title:this.todo.title,description:this.todo.description,date:this.todo.date,done:!1};f.createTodo(t).then(function(t){$.$emit("todo",t)})}}},x={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",[o("b-form",{on:{submit:function(e){return e.preventDefault(),t.createTodo(e)}}},[o("b-form-group",{attrs:{id:"todo-inputs"},model:{value:t.todo,callback:function(e){t.todo=e},expression:"todo"}},[o("b-form-input",{attrs:{id:"todo-title",type:"text",required:"",placeholder:"Enter title"},model:{value:t.todo.title,callback:function(e){t.$set(t.todo,"title",e)},expression:"todo.title"}}),t._v(" "),o("b-form-textarea",{attrs:{id:"todo-description",placeholder:"Enter description"},model:{value:t.todo.description,callback:function(e){t.$set(t.todo,"description",e)},expression:"todo.description"}}),t._v(" "),o("todo-date-picker",{model:{value:t.todo.date,callback:function(e){t.$set(t.todo,"date",e)},expression:"todo.date"}})],1),t._v(" "),o("b-button",{attrs:{id:"todo-add",type:"submit",variant:"success"}},[t._v("Add")])],1)],1)},staticRenderFns:[]};var g={name:"TodoMain",components:{TodoForm:o("VU/8")(y,x,!1,function(t){o("QX1s")},"data-v-8caafeb4",null).exports,TodoFooter:_,TodoList:b,TodoHeader:i}},C={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"container-fluid"},[e("todo-header"),this._v(" "),e("div",{staticClass:"todo-center-with-border"},[e("todo-form")],1),this._v(" "),e("div",{staticClass:"todo-center-with-border"},[e("todo-list")],1),this._v(" "),e("todo-footer")],1)},staticRenderFns:[]};var k={name:"App",components:{TodoMain:o("VU/8")(g,C,!1,function(t){o("plI7")},null,null).exports}},U={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{attrs:{id:"app"}},[e("todo-main")],1)},staticRenderFns:[]};var E=o("VU/8")(k,U,!1,function(t){o("nFGU")},null,null).exports,F=o("e6fC"),w=o.n(F);o("Jmt5"),o("9M+g");o.d(e,"eventBus",function(){return $}),n.a.use(w.a),n.a.config.productionTip=!1;var $=new n.a;new n.a({el:"#app",components:{App:E},template:"<App/>"})},QX1s:function(t,e){},WwAX:function(t,e){},mrZN:function(t,e){},nFGU:function(t,e){},pBZl:function(t,e){},plI7:function(t,e){}},["NHnr"]);
//# sourceMappingURL=app.dd30bb37e8af6db0e271.js.map