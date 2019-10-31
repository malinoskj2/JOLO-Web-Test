<template>
<div id="test">
    <v-btn @click="draw()">Draw</v-btn>
    <v-spacer></v-spacer>
    <canvas id="canvas"></canvas>
</div>
</template>

<script>
export default {
  name: 'DrawTest',
  data() {
    return {

    };
  },
  methods: {
    draw() {
      const canvas = document.getElementById('canvas');
      if (canvas.getContext) {
        const ctx = canvas.getContext('2d');
        ctx.beginPath();
        ctx.strokeStyle = 'black';
        ctx.moveTo(50, 50);
        ctx.lineTo(100, 100);
        ctx.moveTo(100, 100);
        ctx.lineTo(150, 50);
        ctx.stroke();
      }
    },
  },

};
</script>

<style>

</style>
