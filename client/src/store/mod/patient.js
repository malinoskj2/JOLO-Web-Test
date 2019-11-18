export default {
  state: {
    patients: [],
  },
  mutations: {
    savePatients(state, patients) {
      state.patients = patients;
    },
  },
  getters: {
    patients: state => state.patients,
  },
  actions: {
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
  },
};
