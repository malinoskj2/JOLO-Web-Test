<template>
  <v-app id="app">
    <Nav :textA="navConfig.textA"
         :textB="navConfig.textB"
         :items="navConfig.navLinks"
         :isAuthenticated="isAuthenticated">

      <template v-slot:profile-menu>
        <profile-menu :auth-links="navConfig.authLinks"
                      :username="email"
                      :first-name="firstName"
                      :last-name="lastName"
                      @sign-out="$store.commit('signOut')"/>
      </template>
    </Nav>
    <router-view></router-view>
  </v-app>
</template>

<script>

import { mapGetters } from 'vuex';
// eslint-disable-next-line import/no-extraneous-dependencies
import {
  mdiHome, mdiLightbulbOutline, mdiPackageDown,
  mdiAccountPlus, mdiLoginVariant,
  mdiSettings, mdiHelp,
} from '@mdi/js';
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
          { icon: mdiHome, text: 'Home', route: '/' },
          { divider: true },
          { icon: mdiLightbulbOutline, text: 'Start Exam', route: 'exam' },
          { icon: mdiPackageDown, text: 'Results', route: 'results' },
          { divider: true },
          { icon: mdiAccountPlus, text: 'Sign Up', route: 'signup' },
          { icon: mdiLoginVariant, text: 'Log In', route: 'login' },
          { divider: true },
          { icon: mdiSettings, text: 'Settings', route: 'settings' },
          { icon: mdiHelp, text: 'Help', route: 'help' },
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
      'firstName',
      'lastName',
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
