<template>
  <v-menu offset-y>
    <template v-slot:activator="{ on }">
      <v-icon size="2rem" v-on="on">mdi-dots-vertical</v-icon>
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

        <v-list-item>
          <v-list-item-icon>
            <v-icon>close</v-icon>
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
export default {
  name: 'ProfileMenu',
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
