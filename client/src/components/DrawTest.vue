<template>
<div id="test">
   <v-btn @click="recorder.start()">Start</v-btn>
  <v-btn @click="recorder.stop()">Stop</v-btn>
  <v-btn @click="this.submitRecord">Submit</v-btn>
   <v-divider
      vertical></v-divider>
      <v-btn @click="next()">Next</v-btn>
    <v-spacer></v-spacer>
    <canvas id="canvas" style="border:2px solid #000000"></canvas>
</div>
</template>

<script>
import { mapGetters } from 'vuex';
import Recorder from '../services/recorder';

export default {
  name: 'DrawTest',
  props: {
    questions: {
      type: Array,
      required: true,
    },
    testSubmissionID: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      recorder: new Recorder(),
    };
  },
  methods: {
    submitRecord() {
      this.$store.dispatch('makeAuthenticatedCall',
        {
          action: 'submitRecord',
          recording: this.recorder.getLastRecording(),
          testSubmissionID: this.testSubmissionID,
          questionID: this.questions[this.numQuestionsComplete - 1].questionID,
        })
        .then(() => console.log('dispatched submitRecord'))
        .catch(() => console.log('failed to dispatch submitRecord'));
    },
    draw(xStart1, yStart1, xEnd1, yEnd1, xStart2, yStart2, xEnd2, yEnd2) {
      const canvas = document.getElementById('canvas');
      if (canvas.getContext) {
        const ctx = canvas.getContext('2d');
        ctx.canvas.width = 400;
        ctx.canvas.height = 400;
        ctx.beginPath();
        ctx.strokeStyle = 'black';
        ctx.moveTo(xStart1, yStart1);
        ctx.lineTo(xEnd1, yEnd1);
        ctx.moveTo(xStart2, yStart2);
        ctx.lineTo(xEnd2, yEnd2);
        ctx.stroke();
      }
    },
    next() {
      if (this.numQuestionsComplete < this.numQuestions) {
        this.draw(this.questions[this.numQuestionsComplete].line1StartX,
          this.questions[this.numQuestionsComplete].line1StartY,
          this.questions[this.numQuestionsComplete].line1EndX,
          this.questions[this.numQuestionsComplete].line1EndY,
          this.questions[this.numQuestionsComplete].line2StartX,
          this.questions[this.numQuestionsComplete].line2StartY,
          this.questions[this.numQuestionsComplete].line2EndX,
          this.questions[this.numQuestionsComplete].line2EndY);

        this.$store.commit('incrementQuestionsComplete');
      } else {
        this.$store.commit('unsetInProgress');
        this.$store.commit('resetQuestionsComplete');
        this.$router.push('/results');
      }
    },
  },
  computed: {
    ...mapGetters([
      'numQuestionsComplete',
      'numQuestions',
    ]),
  },

};
</script>

<style>

</style>
