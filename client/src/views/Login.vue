<template>
<div id="signup">
    <v-app-bar dense>
            <v-toolbar-title color="">Angle-Matching Web Test</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items class="hidden-sm-and-down">
                <v-btn> Home </v-btn>
                <v-btn> About </v-btn>
                <v-btn> Login </v-btn>
            </v-toolbar-items>
      </v-app-bar>
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
             <v-card-title class="justify-center">Sign Up</v-card-title>
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
const axios = require('axios').default;

export default {
  name: 'Login',
  data: () => ({
    loginCred: {
      Email: '',
      Password: '',
    },
  }),
  methods: {
    submit() {
      console.log(`${this.Email}\n${
        this.Password}`);
    },
    getUserAuth() {
      axios.post('', {
        body: this.loginCred,
      })
        .then((response) => {
          console.log(this.reponse.data);
        })
        .catch((e) => {
          this.errors.push(e);
        });
    },
  },
};
</script>

<style>

</style>
