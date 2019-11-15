<template>
  <v-menu offset-y>
    <template v-slot:activator="{ on }">
      <v-icon size="2rem" v-on="on">{{dotsSvgPath}}</v-icon>
    </template>

    <v-system-bar></v-system-bar>

    <v-list-item link>
      <v-list-item-content>
        <v-list-item-title class="title">{{firstName}}  {{lastName}}</v-list-item-title>
        <v-list-item-subtitle>{{username}}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-divider></v-divider>

    <v-list nav dense>
      <v-list-item-group v-model="item" color="primary">

        <router-link  v-for="(item, i) in authLinks"
                      :key="i" :to="item.route"
                      class="router-link">

          <v-list-item>
            <v-list-item-icon>
              <v-icon v-text="item.icon"></v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title v-text="item.text"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </router-link>

        <v-list-item @click="$emit('sign-out')">
          <v-list-item-icon>
            <v-icon>{{closeSvgPath}}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Sign out</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

      </v-list-item-group>
    </v-list>

  </v-menu>
</template>

<script>
import { mdiWindowClose, mdiDotsVertical } from '@mdi/js';

export default {
  name: 'ProfileMenu',
  data() {
    return {
      closeSvgPath: mdiWindowClose,
      dotsSvgPath: mdiDotsVertical,
    };
  },
  props: {
    authLinks: Array,
    firstName: String,
    lastName: String,
    username: String,
  },
};
</script>

<style scoped>

</style>
