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
      coords: [
        [0, 0],
      ]
      ,
    };
  },
  methods: {
    draw(x, y) {
      const canvas = document.getElementById('canvas');
      if (canvas.getContext) {
        const ctx = canvas.getContext('2d');
        ctx.canvas.width = 200;
        ctx.canvas.height = 200;
        ctx.beginPath();
        ctx.strokeStyle = 'black';
        ctx.moveTo(ctx.canvas.width / 2, ctx.canvas.height / 2);
        ctx.lineTo(x, y);
        ctx.moveTo(ctx.canvas.width / 2, ctx.canvas.height / 2);
        ctx.lineTo(x + 50, y + 50);
        ctx.stroke();
      }
    },
    next() {
      this.draw(this.coords[this.i][0], this.coords[this.i][1]);
      this.i = this.i + 1;
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
