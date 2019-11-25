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
      i: 0,
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
          questionID: this.questions[this.i - 1].questionID,
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
      if (this.i < this.questions.length) {
        this.draw(this.questions[this.i].line1StartX,
          this.questions[this.i].line1StartY,
          this.questions[this.i].line1EndX,
          this.questions[this.i].line1EndY,
          this.questions[this.i].line2StartX,
          this.questions[this.i].line2StartY,
          this.questions[this.i].line2EndX,
          this.questions[this.i].line2EndY);
        this.i = this.i + 1;
      } else {
        this.$router.push('/results');
      }
    },
  },

};
</script>

<style>

</style>
