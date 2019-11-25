export default {
  state: {
    test: {
      questions: [],
      testSubmissionID: 0,
    },
    inProgress: false,
  },
  mutations: {
    saveTest(state, test) {
      state.test = test;
    },
    setInProgress(state) {
      state.inProgress = true;
    },
    unsetInProgress(state) {
      state.inProgress = false;
    },
  },
  getters: {
    test: state => state.test,
    inProgress: state => state.inProgress,
  },
  actions: {
    async fetchTest(context, payload) {
      const response = await fetch(`${process.env.VUE_APP_API}/test/start`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${context.getters.token}`,
        },
        body: JSON.stringify({
          patientID: payload.patientID,
          testID: 1,
        }),
      });

      context.commit('saveTest', await response.json());

      return response.status;
    },
  },
  async submitRecord(context, payload) {
    const formData = new FormData();
    formData.append('file', payload.recording);
    formData.append('testSubmissionID', payload.testSubmissionID);
    formData.append('questionID', payload.questionID);

    const response = await fetch(`${process.env.VUE_APP_API}/test/result`, {
      method: 'POST',
      body: formData,
      headers: {
        Authorization: `Bearer ${context.getters.token}`,
      },
    });

    return response.status;
  },
};
