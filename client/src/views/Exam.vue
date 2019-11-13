<template>
  <div class="my-auto mx-auto">
    <DrawTest :questions="test.questions"
              :testSubmissionID="test.testSubmissionID"/>
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
  data() {
    return {
      test: {},
    };
  },
  methods: {
    async fetchTest() {
      const response = await fetch('http://localhost:8081/test/start', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${this.$store.state.token}`,
        },
        body: JSON.stringify({
          patientID: Math.floor(Math.random() * Math.floor(10)),
          testID: 1,
        }),
      });
      this.test = await response.json();
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
  mounted() {
    this.fetchTest();
    this.draw();
  },
};
</script>

<style>

</style>
