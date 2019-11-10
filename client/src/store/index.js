import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: null,
    email: null,
  },
  mutations: {
    saveToken(state, token) {
      state.token = token;
    },
    saveEmail(state, email) {
      state.email = email;
    },
  },
  getters: {
    token: state => state.token,
    email: state => state.email,
  },
  actions: {
  },
  modules: {
  },
});
