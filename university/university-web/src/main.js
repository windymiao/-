import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import VueRouter from "vue-router";
import router from "@/router";
import axios from 'axios';
import store from "./store"

Vue.prototype.$axios =axios
Vue.prototype.$httpUrl='http://localhost:8088'
Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'});
Vue.use(VueRouter)

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
