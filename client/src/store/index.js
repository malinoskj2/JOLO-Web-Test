import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersistence from 'vuex-persist';

const vuexLocal = new VuexPersistence({
  storage: window.localStorage,
});

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    test: {
      questions: [],
      testSubmissionID: 0,
    },
    patients: [],
  },
  mutations: {
    saveTest(state, test) {
      state.test = test;
    },
    savePatients(state, patients) {
      state.patients = patients;
    },
  },
  getters: {
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
