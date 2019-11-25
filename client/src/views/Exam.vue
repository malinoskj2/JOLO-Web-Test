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
            <v-btn color="primary" text @click="next">Yes</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <DrawTest :questions="test.questions"
                :testSubmissionID="test.testSubmissionID"/>
      <v-spacer></v-spacer>
      <canvas id="canvas2"></canvas>
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
    start(payload) {
      this.$store.dispatch('makeAuthenticatedCall', {
        action: 'fetchTest',
        patientID: payload.patientID,
      })
        .then(() => console.log('fetched test'))
        .catch(() => console.log('error fetching test'));
      this.draw();
      this.$store.commit('setInProgress');
    },
    draw() {
      const canvas = document.getElementById('canvas2');
      if (canvas.getContext) {
        const ctx = canvas.getContext('2d');
        ctx.canvas.width = 400;
        ctx.canvas.height = 400;
        ctx.beginPath();
        ctx.strokeStyle = 'black';
        // Line 1
        ctx.moveTo(170, 200);
        ctx.lineTo(80, 200);
        // // Line 2
        ctx.moveTo(172, 192);
        ctx.lineTo(82, 170);
        // // Line 3
        ctx.moveTo(176, 186);
        ctx.lineTo(96, 138);
        // // Line 4
        ctx.moveTo(180, 182);
        ctx.lineTo(116, 114);
        // // Line 5
        ctx.moveTo(186, 178);
        ctx.lineTo(152, 98);
        // // Line 6
        ctx.moveTo(194, 178);
        ctx.lineTo(194, 90);
        // // Line 7
        ctx.moveTo(200, 178);
        ctx.lineTo(232, 98);
        // // Line 8
        ctx.moveTo(208, 182);
        ctx.lineTo(266, 114);
        // // Line 9
        ctx.moveTo(212, 186);
        ctx.lineTo(286, 138);
        // // Line 10
        ctx.moveTo(214, 192);
        ctx.lineTo(300, 170);
        // Line 11
        ctx.moveTo(216, 200);
        ctx.lineTo(310, 200);
        ctx.stroke();
      }
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
