export default {
  state: {
    test: {
      questions: [],
      testSubmissionID: 0,
    },
  },
  mutations: {
    saveTest(state, test) {
      state.test = test;
    },
  },
  getters: {
    test: state => state.test,
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
};
