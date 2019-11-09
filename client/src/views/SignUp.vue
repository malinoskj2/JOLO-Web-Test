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

export default {
  name: 'signup',
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
      console.log('Did fetch');
      fetch('http://localhost:8081/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          fName: this.fName,
          lName: this.lName,
          password: this.Password,
          email: this.Email,
        }),
      })
        .then((response) => {
          console.log('got response');
          console.log(response);
          console.log(response.status);
          if (response.status === 200) {
            console.log(response.status);
            this.$router.push('/login');
          }
        })
        .catch(error => console.log(error));
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
