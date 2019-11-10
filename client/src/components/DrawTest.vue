<template>
<div id="test">
   <v-btn @click="getTestCoordinates()">Start</v-btn>
   <v-divider
      vertical></v-divider>
      <v-btn @click="next()">Next</v-btn>
    <v-spacer></v-spacer>
    <canvas id="canvas" style="border:2px solid #000000"></canvas>
</div>
</template>

<script>
const axios = require('axios').default;

export default {
  name: 'DrawTest',
  data() {
    return {
      i: 0,
      testExample: {
        testSubmissionID: 0,
        questions: [
          {
            xStart1: 170,
            yStart1: 200,
            xEnd1: 80,
            yEnd1: 200,
            xStart2: 194,
            yStart2: 178,
            xEnd2: 193,
            yEnd2: 90,
          },
          {
            xStart1: 176,
            yStart1: 186,
            xEnd1: 96,
            yEnd1: 138,
            xStart2: 180,
            yStart2: 182,
            xEnd2: 116,
            yEnd2: 114,
          },
        ],
      }
      ,
    };
  },
  methods: {
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
        ctx.moveTo(xStart2, yEnd2);
        ctx.lineTo(xEnd2, yEnd2);
        ctx.stroke();
      }
    },
    next() {
      if (this.i < this.testExample.questions.length) {
        console.log(this.testExample.questions[this.i]);
        this.draw(this.testExample.questions[this.i].xStart1,
          this.testExample.questions[this.i].yStart1,
          this.testExample.questions[this.i].xEnd1,
          this.testExample.questions[this.i].yEnd1,
          this.testExample.questions[this.i].xStart2,
          this.testExample.questions[this.i].yStart2,
          this.testExample.questions[this.i].xEnd2,
          this.testExample.questions[this.i].yEnd2);
        this.i = this.i + 1;
      }
    },
    getTestCoordinates() {
      axios.get()
        .then((response) => {
          this.coords = response.data;
        });
    },
  },

};
</script>

<style>

</style>
