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
                v-model="Email"
                label="Email"
                name="email"
                type="email"
                required
                />
                 <v-text-field
                 v-model="Password"
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
  data: () => ({
    loginCred: {
      Email: '',
      Password: '',
    },
  }),
  methods: {
    getUserAuth() {
      fetch('http://localhost:8081/auth/authenticate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: this.Email,
          password: this.Password,
        }),
      })
        .then(response => response.json())
        .then((token) => {
          console.log(token.token);
          this.$store.commit('saveEmail', this.Email);
          this.$store.commit('saveToken', token.token);
        })
        .catch(error => console.log(error));
    },
    submit() {
      this.getUserAuth();
    },
  },
};
</script>

<style>

</style>
