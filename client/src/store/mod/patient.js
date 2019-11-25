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

      return response.status;
    },
    async isIdAvailable(context, payload) {
      const response = await fetch(`${process.env.VUE_APP_API}/patient/existsID`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${context.getters.token}`,
        },
        body: JSON.stringify({
          patientID: payload.patientID,
        }),
      });

      const inUse = await response.json();
      return Boolean(!inUse);
    },
  },
};
