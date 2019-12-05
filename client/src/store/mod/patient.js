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

      const getTrials = async (patientID) => {
        const resp = await fetch(`${process.env.VUE_APP_API}/test/trials?patientID=${patientID}`,
          {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${context.getters.token}`,
            },
          });
        const trials = await resp.json();
        return {
          ID: patientID,
          trials: trials.sort((a, b) => a.questionID - b.questionID)
            .map(trial => ({ patientID, ...trial })),
        };
      };

      const patients = await Promise.all(json.patients.map(patientID => getTrials(patientID)));

      context.commit('savePatients', patients);

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
