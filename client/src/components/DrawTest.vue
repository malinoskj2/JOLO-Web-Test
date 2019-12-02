<template>
<div id="test">
    <canvas id="canvas"></canvas>
</div>
</template>

<script>

export default {
  name: 'DrawTest',
  props: {
    question: {
      type: Object,
      required: true,
    },
    canvasWidth: {
      type: Number,
      required: true,
    },
    canvasHeight: {
      type: Number,
      required: true,
    },
  },
  mounted() {
    this.setDimensions();
  },
  methods: {
    setDimensions() {
      const canvas = document.getElementById('canvas');
      const ctx = canvas.getContext('2d');
      ctx.canvas.width = this.canvasWidth;
      ctx.canvas.height = this.canvasHeight;
    },
    draw() {
      const {
        line1StartX, line1StartY,
        line1EndX, line1EndY,
        line2StartX, line2StartY,
        line2EndX, line2EndY,
      } = this.question;

      const canvas = document.getElementById('canvas');

      if (canvas.getContext) {
        const ctx = canvas.getContext('2d');
        this.setDimensions();
        ctx.lineWidth = 3;
        ctx.lineCap = 'round';
        ctx.beginPath();
        ctx.strokeStyle = 'black';
        ctx.moveTo(line1StartX, line1StartY);
        ctx.lineTo(line1EndX, line1EndY);
        ctx.moveTo(line2StartX, line2StartY);
        ctx.lineTo(line2EndX, line2EndY);
        ctx.font = 'bold 17px Oswald';
        // Line 1
        ctx.fillText('1', 163, 685);
        ctx.moveTo(177, 682);
        ctx.lineTo(288, 682);
        // Line 2
        ctx.fillText('2', 170, 639);
        ctx.moveTo(184, 638);
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
        ctx.moveTo(431, 638);
        ctx.lineTo(328, 676);
        // Line 11
        ctx.fillText('11', 445, 685);
        ctx.moveTo(438, 682);
        ctx.lineTo(330, 682);
        ctx.stroke();
      }
    },
  },
  watch: {
    question() {
      this.draw();
    },
  },
};
</script>

<style>

</style>
