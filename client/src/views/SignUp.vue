<template>
<div id="signup">
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
                v-model="userData.fName"
                label="First Name"
                name="fName"
                type="text"
                required
                />
                 <v-text-field
                v-model="userData.lName"
                label="Last Name"
                name="lNamee"
                type="text"
                required
                />
                 <v-text-field
                v-model="userData.email"
                label="Email"
                name="Email"
                type="email"
                required
                />
                 <v-text-field
                 v-model="userData.password"
                label="Password"
                name="Password"
                type="password"
                required
                />
                <v-text-field
                v-model="userData.retypedPassword"
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
                <v-btn class="justify-center" color="primary" @click="postUserData()">Submit</v-btn>
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

export default {
  name: 'signup',
  metaInfo() {
    return {
      title: 'Sign Up',
    };
  },
  data: () => ({
    userData: {
      fName: '',
      lName: '',
      email: '',
      password: '',
      retypedPassword: '',
    },
  }),
  methods: {
    postUserData() {
      this.$store.dispatch('signup',
        {
          successFunction: () => this.$router.push('/login'),
          ...this.userData,
        })
        .then(() => console.log('dispatched signup'))
        .catch(() => console.log('failed to dispatch signup'));
    },
  },
};
</script>

<style>

</style>
