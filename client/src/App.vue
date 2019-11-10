<template>
  <v-app id="app">
    <Nav :textA="this.navConfig.textA"
         :textB="this.navConfig.textB"
         :items="this.navConfig.navLinks"
         :authLinks="this.navConfig.authLinks"
         :token="this.$store.state.token"
         :username="this.$store.state.email"/>
    <router-view></router-view>
  </v-app>
</template>

<script>
import Nav from '@/components/Nav.vue';

export default {
  name: 'App',
  data() {
    return {
      navConfig: {
        textA: 'Angle',
        textB: 'Test',
        navLinks: [
          { icon: 'home', text: 'Home', route: '/' },
          { divider: true },
          { icon: 'lightbulb_outline', text: 'Start Exam', route: 'exam' },
          { icon: 'archive', text: 'Results', route: 'results' },
          { divider: true },
          { icon: 'mdi-account-plus', text: 'Sign Up', route: 'signup' },
          { icon: 'mdi-login-variant', text: 'Log In', route: 'login' },
          { divider: true },
          { icon: 'settings', text: 'Settings', route: 'settings' },
          { icon: 'help', text: 'Help', route: 'help' },
        ],
        authLinks: [
          { text: 'Account', icon: 'mdi-account-card-details', route: 'account' },
        ],
      },
    };
  },
  components: {
    Nav,
  },

};
</script>
<style>
  .router-link {
    color: inherit;
    text-decoration: inherit;
    outline: none;
  }
</style>
