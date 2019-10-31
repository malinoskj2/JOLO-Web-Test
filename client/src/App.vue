<template>
  <v-app>
    <v-content>
      <DrawTest/>
    </v-content>
  </v-app>
</template>

<script>
// import SignUp from './views/SignUp.vue';
import DrawTest from './components/DrawTest.vue';

export default {
  name: 'App',
  components: {
    DrawTest,
    // SignUp,

  },
  data: () => ({
    //
  }),
};
</script>
