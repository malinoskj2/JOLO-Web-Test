export default {
  state: {
    test: {
      questions: [
        { },
      ],
      testSubmissionID: 0,
    },
    answeredQuestions: new Set(),
    numAnsweredQuestions: 0,
    inProgress: false,
    activeQuestion: 0,
  },
  mutations: {
    saveTest(state, test) {
      state.test = test;
    },
    setInProgress(state) {
      state.inProgress = true;
    },
    resetTest(state) {
      state.test = {
        questions: [],
        testSubmissionID: 0,
      };
      state.inProgress = false;
      state.questionsComplete = 0;
      state.activeQuestion = 0;
    },
    resetQuestionsComplete(state) {
      state.questionsComplete = 0;
    },
    incrementActiveQuestion(state) {
      if (state.activeQuestion < state.test.questions.length - 1) {
        state.activeQuestion += 1;
      }
    },
    decrementActiveQuestion(state) {
      if (state.activeQuestion > 0) {
        state.activeQuestion -= 1;
      }
    },
    setQuestionAnswered(state, questionID) {
      state.answeredQuestions = state.answeredQuestions.add(questionID);
      state.numAnsweredQuestions = state.numAnsweredQuestions.size;
    },
  },
  getters: {
    test: state => state.test,
    inProgress: state => state.inProgress,
    numQuestionsComplete: state => state.numAnsweredQuestions,
    numQuestions: state => state.test.questions.length,
    testSubmissionID: state => state.test.testSubmissionID,
    hasPreviousQuestion: state => state.activeQuestion > 0,
    hasNextQuestion: state => state.activeQuestion < state.test.questions.length - 1,
    currentQuestion: state => state.test.questions[state.activeQuestion],
    currentTrialNumber: state => state.activeQuestion + 1,
    onLastQuestion: state => state.activeQuestion === state.test.questions.length - 1,
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
    // eslint-disable-next-line no-unused-vars
    async submitRecord2(context) {
      await console.log('submitRecord2 fired');
    },
  },
};
