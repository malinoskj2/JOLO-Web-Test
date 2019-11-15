<template>
  <v-app id="app">
    <Nav :textA="navConfig.textA"
         :textB="navConfig.textB"
         :items="navConfig.navLinks"
         :isAuthenticated="isAuthenticated">

      <template v-slot:profile-menu>
        <profile-menu :auth-links="navConfig.authLinks"
                      :username="email"
                      @sign-out="$store.commit('signOut')"/>
      </template>
    </Nav>
    <router-view></router-view>
  </v-app>
</template>

<script>

import { mapGetters } from 'vuex';
import Nav from '@/components/Nav.vue';
import ProfileMenu from './components/ProfileMenu.vue';

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
  computed: {
    ...mapGetters([
      'email',
      'isAuthenticated',
    ]),
  },
  components: {
    Nav,
    ProfileMenu,
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
