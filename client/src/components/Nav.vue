<template>
  <div>
    <v-app-bar app clipped-left color="white" light
               class="elevation-1">
      <v-app-bar-nav-icon @click="drawer = !drawer" color="primary"/>
      <span class="title ml-3 mr-5">{{textA}}&nbsp;
        <span class="font-weight-light">{{textB}}</span>
      </span>
      <v-text-field id="search-bar"
                    @focus="showOutline"
                    @blur="hideOutline"
                    :outlined="outline"
                    background-color="secondary"
                    solo
                    flat
                    hide-details
                    label="Search"
                    prepend-inner-icon="search"/>

      <v-spacer/>
      <div v-if="this.isAuthenticated">
        <profile-menu :auth-links="authLinks"
                      :first-name="firstName"
                      :last-name="lastName"
                      :username="username"/>
      </div>
      <div v-else>
        <router-link to="/login">
          <v-btn depressed color="secondary" class="black--text mr-3 text-none">Log In</v-btn>
        </router-link>
        <router-link to="/signup">
          <v-btn depressed color="primary" class="white--text text-none">Sign Up</v-btn>
        </router-link>
      </div>

    </v-app-bar>

    <v-navigation-drawer v-model="drawer" app clipped color="grey lighten-4">
      <v-list
        dense
        class="grey lighten-4"
      >
        <template v-for="(item, i) in items">

          <v-row
            v-if="item.heading"
            :key="i"
            align="center"
          >
            <v-col cols="6">
              <v-subheader v-if="item.heading">
                {{ item.heading }}
              </v-subheader>
            </v-col>
            <v-col cols="6" class="text-right">
              <v-btn small text>edit</v-btn>
            </v-col>
          </v-row>
          <v-divider
            v-else-if="item.divider"
            :key="i"
            dark
            class="my-4"
          />


          <router-link v-else
                       :key="i"
                       :to="item.route"
                       class="router-link"
                       exact-active-class="red--text">
            <v-list-item :key="i" link
            active-class="red--text"
            exact-active-class="red--text">
              <v-list-item-action>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title class="grey--text">
                  {{ item.text }}
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </router-link>

        </template>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import ProfileMenu from './ProfileMenu.vue';

export default {
  name: 'Nav',
  data() {
    return {
      drawer: null,
      outline: false,
    };
  },
  methods: {
    async showOutline() {
      this.outline = true;
      await this.$nextTick();
    },
    async hideOutline() {
      this.outline = false;
      await this.$nextTick();
    },
  },
  components: {
    ProfileMenu,
  },
  props: {
    textA: {
      type: String,
      required: true,
    },
    textB: {
      type: String,
      required: true,
    },
    items: {
      type: Array,
      required: true,
    },
    authLinks: {
      type: Array,
      required: true,
    },
    isAuthenticated: {
      type: Boolean,
      required: false,
    },
    username: {
      type: String,
      required: false,
    },
    firstName: {
      type: String,
      required: false,
    },
    lastName: {
      type: String,
      required: false,
    },

  },

};
</script>

<style scoped>
</style>
