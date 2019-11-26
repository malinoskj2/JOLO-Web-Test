<template>
    <div class="my-auto mx-auto">
      <patient-i-d-prompt @set-patient="start"/>
      <v-dialog v-model="exitGuard" persistent max-width="290">
        <v-card>
          <v-card-title class="headline">Quitting The Exam?</v-card-title>
          <v-card-text>Only completed trials will be recorded.</v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" text @click="exitGuard = false">No</v-btn>
            <v-btn color="primary" text @click="exitExam">Yes</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <DrawTest :questions="test.questions"
                :testSubmissionID="test.testSubmissionID"/>
      <v-spacer></v-spacer>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import DrawTest from '@/components/DrawTest.vue';
import PatientIDPrompt from '@/components/PatientIDPrompt.vue';

export default {
  name: 'exam',
  // eslint-disable-next-line no-unused-vars
  beforeRouteLeave(to, from, next) {
    if (this.$store.getters.inProgress) {
      this.next = next;
      this.exitGuard = true;
    } else {
      next();
    }
  },
  components: {
    DrawTest,
    PatientIDPrompt,
  },
  data() {
    return {
      exitGuard: false,
      next: {},
    };
  },
  methods: {
    exitExam() {
      this.next();
      this.$store.commit('unsetInProgress');
      this.$store.commit('resetQuestionsComplete');
    },
    start(payload) {
      this.$store.dispatch('makeAuthenticatedCall', {
        action: 'fetchTest',
        patientID: payload.patientID,
      })
        .then(() => console.log('fetched test'))
        .catch(() => console.log('error fetching test'));
      this.$store.commit('setInProgress');
    },
  },
  computed: {
    ...mapGetters([
      'test',
      'inProgress',
    ]),
  },
};
</script>

<style>

</style>
