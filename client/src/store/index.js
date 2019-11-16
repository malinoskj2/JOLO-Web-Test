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
    test: {
      questions: [],
      testSubmissionID: 0,
    },
    patients: [],
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
    saveTest(state, test) {
      state.test = test;
    },
    savePatients(state, patients) {
      state.patients = patients;
    },
  },
  getters: {
    token: state => state.token,
    email: state => state.email,
    firstName: state => state.firstName,
    lastName: state => state.lastName,
    isAuthenticated: state => Boolean(state.token),
    test: state => state.test,
    patients: state => state.patients,
  },
  actions: {
    async fetchTest(context, patientID) {
      const response = await fetch(`${process.env.VUE_APP_API}/test/start`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${context.getters.token}`,
        },
        body: JSON.stringify({
          patientID,
          testID: 1,
        }),
      });

      context.commit('saveTest', await response.json());
    },
    async login(context, payload) {
      const response = await fetch(`${process.env.VUE_APP_API}/auth/authenticate`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: payload.email,
          password: payload.password,
        }),
      });

      const json = await response.json();
      context.commit('saveEmail', json.email);
      context.commit('saveToken', json.token);
      context.commit('saveFirstName', json.firstName);
      context.commit('saveLastName', json.lastName);
    },
    async signup(context, payload) {
      const response = await fetch(`${process.env.VUE_APP_API}/auth/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          fName: payload.fName,
          lName: payload.lName,
          password: payload.password,
          email: payload.email,
        }),
      });

      if (response.ok) {
        payload.successFunction();
      }
    },
    async fetchPatients(context) {
      const response = await fetch(`${process.env.VUE_APP_API}/patient/all`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${context.getters.token}`,
        },
      });

      const json = await response.json();

      context.commit('savePatients',
        json.patients.map(patientID => ({ ID: patientID })));
    },
    async submitRecord(context, payload) {
      const formData = new FormData();
      formData.append('file', payload.recording);
      formData.append('testSubmissionID', payload.testSubmissionID);
      formData.append('questionID', payload.questionID);

      return fetch(`${process.env.VUE_APP_API}/test/result`, {
        method: 'POST',
        body: formData,
        headers: {
          Authorization: `Bearer ${context.getters.token}`,
        },
      });
    },
  },
  modules: { },
  plugins: [vuexLocal.plugin],
});
