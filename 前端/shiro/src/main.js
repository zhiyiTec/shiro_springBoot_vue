// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
// import router from './router'
import $ from 'jquery'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import axios from 'axios'
import VueRouter from 'vue-router'
import Login from '@/components/Login'
import success from './components/success.vue'
import personalResign from './components/personalResign.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import test from './components/test'


Vue.config.productionTip = false
Vue.use(BootstrapVue)
Vue.prototype.$axios = axios
Vue.prototype.HOST = '/api'
Vue.use(VueRouter)
Vue.use(ElementUI);
/* eslint-disable no-new */





const routes = [
    { path: '/success', component: success },
    { path: '/', component: Login },
    { path: '/personalResign', component: personalResign },
    { path: '/test', component: test }
    //前面to指定的地方 path  /1
]


const router = new VueRouter({
    mode: 'history',
    base: __dirname,
    routes
})
new Vue({
    el: '#app',
    router,
    components: { App },
    template: '<App/>'
})