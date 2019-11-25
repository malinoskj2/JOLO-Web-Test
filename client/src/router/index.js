import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/Home.vue'),
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import('../views/SignUp.vue'),
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue'),
  },
  {
    path: '/results',
    name: 'results',
    component: () => import('../views/Results.vue'),
  },
  {
    path: '/exam',
    name: 'exam',
    component: () => import('../views/Exam.vue'),
  },
  {
    path: '/examInfo',
    name: 'examInfo',
    component: () => import('../views/CompletedExamInfo.vue'),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
