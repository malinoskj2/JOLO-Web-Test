<template>
<div class="my-auto mx-auto" >
  <DrawTest />
  <v-spacer></v-spacer>
  <canvas id="canvas2" style="border:2px solid #000000"></canvas>
</div>
</template>

<script>
import DrawTest from '@/components/DrawTest.vue';

export default {
  name: 'exam',
  components: {
    DrawTest,
  },
  methods: {
    draw() {
      const canvas = document.getElementById('canvas2');
      if (canvas.getContext) {
        const ctx = canvas.getContext('2d');
        ctx.canvas.width = 200;
        ctx.canvas.height = 200;
        ctx.beginPath();
        ctx.strokeStyle = 'black';
        // Line 1
        ctx.moveTo(85, 100);
        ctx.lineTo(40, 100);
        // // Line 2
        ctx.moveTo(86, 96);
        ctx.lineTo(41, 85);
        // // Line 3
        ctx.moveTo(88, 93);
        ctx.lineTo(48, 69);
        // // Line 4
        ctx.moveTo(90, 91);
        ctx.lineTo(58, 57);
        // // Line 5
        ctx.moveTo(93, 89);
        ctx.lineTo(76, 49);
        // // Line 6
        ctx.moveTo(97, 89);
        ctx.lineTo(97, 45);
        // // Line 7
        ctx.moveTo(100, 89);
        ctx.lineTo(116, 49);
        // // Line 8
        ctx.moveTo(104, 91);
        ctx.lineTo(133, 57);
        // // Line 9
        ctx.moveTo(106, 93);
        ctx.lineTo(143, 69);
        // // Line 10
        ctx.moveTo(107, 96);
        ctx.lineTo(150, 85);
        // Line 11
        ctx.moveTo(108, 100);
        ctx.lineTo(155, 100);
        ctx.stroke();
      }
    },
  },
  mounted() {
    this.draw();
  },
};
</script>

<style>

</style>
