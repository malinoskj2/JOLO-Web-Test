import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersistence from 'vuex-persist';

const vuexLocal = new VuexPersistence({
  storage: window.localStorage,
});

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: null,
    email: null,
    firstName: null,
    lastName: null,
  },
  mutations: {
    saveToken(state, token) {
      state.token = token;
    },
    saveEmail(state, email) {
      state.email = email;
    },
    saveFirstName(state, firstName) {
      state.firstName = firstName;
    },
    saveLastName(state, lastName) {
      state.lastName = lastName;
    },
    signOut(state) {
      state.email = null;
      state.token = null;
    },
  },
  getters: {
    token: state => state.token,
    email: state => state.email,
    firstName: state => state.firstName,
    lastName: state => state.lastName,
    isAuthenticated: state => Boolean(state.token),
  },
  actions: {
  },
  modules: {
  },
  plugins: [vuexLocal.plugin],
});
