import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store/mod/user';

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
    beforeEnter(to, from, next) {
      if (store.getters.isAuthenticated) {
        next();
      } else {
        next({
          name: 'login',
        });
      }
    },
  },
  {
    path: '/exam',
    name: 'exam',
    component: () => import('../views/Exam.vue'),
    beforeEnter(to, from, next) {
      if (store.getters.isAuthenticated) {
        next();
      } else {
        next({
          name: 'login',
        });
      }
    },
  },
];

const router = new VueRouter({
  routes,
});

export default router;
