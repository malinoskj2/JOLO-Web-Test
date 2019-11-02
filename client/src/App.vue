<template>
  <v-app>
    <v-content>
      <SignUp/>
    </v-content>
  </v-app>
</template>

<script>
// import SignUp from './views/SignUp.vue';
import SignUp from './views/SignUp.vue';

export default {
  name: 'App',
  components: {
    // DrawTest,
    SignUp,

  },
  data: () => ({
    //
  }),
};
</script>
