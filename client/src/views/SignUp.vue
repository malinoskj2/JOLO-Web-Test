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
                v-model="fName"
                label="First Name"
                name="fName"
                type="text"
                required
                />
                 <v-text-field
                v-model="lName"
                label="Last Name"
                name="lNamee"
                type="text"
                required
                />
                 <v-text-field
                v-model="Email"
                label="Email"
                name="Email"
                type="email"
                required
                />
                 <v-text-field
                 v-model="Password"
                label="Password"
                name="Password"
                type="password"
                required
                />
                <v-text-field
                v-model="retypePassword"
                label="Retype Password"
                name="retypePassword"
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
  name: 'SignUp',
  data: () => ({
    reponseData: {},
    userData: {
      fName: '',
      lName: '',
      Email: '',
      Password: '',
    },
  }),
  methods: {
    postUserData() {
      axios.post('localhost:8081/auth/register', {
        body: this.userData,
      })
        .then((response) => {
          console.log(response);
        })
        .catch((e) => {
          this.errors.push(e);
        });
    },
    submit() {
      console.log(`${this.fName}\n${
        this.lName}\n${
        this.Email}\n${
        this.Password}\n${
        this.retypePassword}`);
      this.postUserData();
    },
  },
};
</script>

<style>

</style>
