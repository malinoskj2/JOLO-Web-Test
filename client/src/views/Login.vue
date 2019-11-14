<template>
<div id="login">
  <v-content>
      <v-container>
         <v-row
          align="center"
          justify="center"
        >
          <v-col
            cols="12"
            sm="8"
            md="4"
          >
         <v-card>
             <v-flex>
             <v-card-title class="justify-center">Login</v-card-title>
             </v-flex>
          <v-card-text>
          <v-form>
                 <v-text-field
                v-model="loginCred.email"
                label="Email"
                name="email"
                type="email"
                required
                />
                 <v-text-field
                 v-model="loginCred.password"
                label="Password"
                name="password"
                type="password"
                required
                />
            </v-form>
          </v-card-text>
          <v-card-actions >
                <v-spacer />
                <v-flex>
                <v-btn class="justify-center" color="primary" @click="submit()">Submit</v-btn>
                </v-flex>
              </v-card-actions>
         </v-card>
          </v-col>
         </v-row>
      </v-container>
  </v-content>
</div>
</template>

<script>
// const axios = require('axios').default;

export default {
  name: 'login',
  metaInfo() {
    return {
      title: 'Log In',
    };
  },
  data: () => ({
    loginCred: {
      email: '',
      password: '',
    },
  }),
  methods: {
    getUserAuth() {
      fetch(`${process.env.VUE_APP_API}/auth/authenticate`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: this.loginCred.email,
          password: this.loginCred.password,
        }),
      })
        .then(response => response.json())
        .then((token) => {
          this.$store.commit('saveEmail', this.loginCred.email);
          this.$store.commit('saveToken', token.token);
        })
        // eslint-disable-next-line no-unused-vars
        .catch((error) => {});
    },
    submit() {
      this.getUserAuth();
    },
  },
};
</script>

<style>

</style>
