<template>
<div id="test">
   <v-btn @click="recorder.start()">Start</v-btn>
  <v-btn @click="recorder.stop()">Stop</v-btn>
  <v-btn @click="this.submitRecord">Submit</v-btn>
   <v-divider
      vertical></v-divider>
      <v-btn @click="next()">Next</v-btn>
    <v-spacer></v-spacer>
    <canvas id="canvas"></canvas>
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
        ctx.canvas.width = 613;
        ctx.canvas.height = 797;
        ctx.lineWidth = 3;
        ctx.lineCap = 'round';
        ctx.beginPath();
        ctx.strokeStyle = 'black';
        ctx.moveTo(xStart1, yStart1);
        ctx.lineTo(xEnd1, yEnd1);
        ctx.moveTo(xStart2, yStart2);
        ctx.lineTo(xEnd2, yEnd2);
        ctx.font = '500 17px Arial';
        // Line 1
        ctx.fillText('1', 163, 685);
        ctx.moveTo(177, 682);
        ctx.lineTo(288, 681);
        // Line 2
        ctx.fillText('2', 170, 645);
        ctx.moveTo(184, 642);
        ctx.lineTo(288, 676);
        // Line 3
        ctx.fillText('3', 188, 603);
        ctx.moveTo(201, 607);
        ctx.lineTo(295, 667);
        // Line 4
        ctx.fillText('4', 223, 569);
        ctx.moveTo(233, 575);
        ctx.lineTo(299, 663);
        // Line 5
        ctx.fillText('5', 262, 548);
        ctx.moveTo(269, 555);
        ctx.lineTo(305, 660);
        // Line 6
        ctx.fillText('6', 304, 542);
        ctx.moveTo(309, 550);
        ctx.lineTo(310, 659);
        // Line 7
        ctx.fillText('7', 349, 548);
        ctx.moveTo(348, 554);
        ctx.lineTo(315, 661);
        // Line 8
        ctx.fillText('8', 387, 566);
        ctx.moveTo(385, 571);
        ctx.lineTo(320, 663);
        // Line 9
        ctx.fillText('9', 419, 600);
        ctx.moveTo(413, 600);
        ctx.lineTo(325, 666);
        // Line 10
        ctx.fillText('10', 436, 639);
        ctx.moveTo(431, 636);
        ctx.lineTo(328, 671);
        // Line 11
        ctx.fillText('11', 445, 680);
        ctx.moveTo(438, 675);
        ctx.lineTo(330, 677);
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
