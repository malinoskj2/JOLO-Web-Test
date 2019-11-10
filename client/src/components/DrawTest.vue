<template>
<div id="test">
   <v-btn @click="this.startRecord">Start</v-btn>
  <v-btn @click="this.stopRecord">Stop</v-btn>
  <v-btn @click="this.submitRecord">Submit</v-btn>
   <v-divider
      vertical></v-divider>
      <v-btn @click="next()">Next</v-btn>
    <v-spacer></v-spacer>
    <canvas id="canvas" style="border:2px solid #000000"></canvas>
</div>
</template>

<script>
import polyfill from 'audio-recorder-polyfill';

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
      recorder: {},
      recordings: [],
      lastRecording: {},
      testExample: {
        testSubmissionID: 0,
      },
    };
  },
  created() {
    this.initRecorder();
  },
  methods: {
    initRecorder() {
      window.MediaRecorder = polyfill;
      navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
        this.recorder = new MediaRecorder(stream);
        this.recorder.addEventListener('dataavailable', (e) => {
          this.lastRecording = e.data;
        });
      });
    },
    startRecord() {
      this.recorder.start();
    },
    stopRecord() {
      this.recorder.stop();
    },
    printRecord() {
      console.log(this.lastRecording);
    },
    submitRecord() {
      const formData = new FormData();
      console.log('Submitting Answer');
      console.log(this.lastRecording);
      formData.append('file', this.lastRecording);
      formData.append('testSubmissionID', this.testSubmissionID);
      formData.append('questionID', this.questions[0].questionID);

      fetch('http://localhost:8081/test/result', {
        method: 'POST',
        body: formData,
        headers: {
          Authorization: `Bearer ${this.$store.state.token}`,
        },
      })
        .then(() => console.log('submitted answer'))
        .catch(() => console.log('failed to submit answer'));
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
        console.log(this.questions[this.i]);
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
        console.log('Test Complete');
        this.$router.push('/results');
      }
    },
  },

};
</script>

<style>

</style>
