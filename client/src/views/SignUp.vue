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
          <v-form ref="form" lazy-validation>
              <v-text-field
                v-model="userData.fName"
                label="First Name"
                name="fName"
                type="text"
                :rules="firstNameRules"
                required
                />
                 <v-text-field
                v-model="userData.lName"
                label="Last Name"
                name="lName"
                type="text"
                :rules="lastNameRules"
                required
                />
                 <v-text-field
                v-model="userData.email"
                label="Email"
                name="Email"
                type="email"
                required
                :rules="emailRules"
                />
                 <v-text-field
                 v-model="userData.password"
                label="Password"
                name="Password"
                type="password"
                :rules="passwordRules"
                required
                />
                <v-text-field
                v-model="userData.retypedPassword"
                label="Retype Password"
                name="retypePassword"
                type="password"
                :rules="passwordRules"
                required
                />
            </v-form>
          </v-card-text>
          <v-card-actions @keydown.enter="submit()">
                <v-spacer />
                <v-flex>
                <v-btn
                class="justify-center"
                color="primary"
                @click="submit()">
                Submit
                </v-btn>
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
  data() {
    return {
      userData: {
        fName: '',
        lName: '',
        email: '',
        password: '',
        retypedPassword: '',
      },
      valid: true,
      firstNameRules: [
        v => !!v || 'Name is required',
      ],
      lastNameRules: [
        v => !!v || 'Last name is required',
      ],
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
      ],
      passwordRules: [
        v => !!v || 'Password is required',
        v => (v && v.length >= 8) || 'Password must be longer 8 characters or longer.',
      ],
    };
  },
  methods: {
    autoLogin() {
      fetch('http://localhost:8081/auth/authenticate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: this.userData.email,
          password: this.userData.password,
        }),
      })
        .then(response => response.json())
        .then((token) => {
          this.$store.commit('saveEmail', this.userData.email);
          this.$store.commit('saveToken', token.token);
          this.$router.push('/results');
        })
        // eslint-disable-next-line no-unused-vars
        .catch((error) => {});
    },
    postUserData() {
      fetch('http://localhost:8081/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          fName: this.userData.fName,
          lName: this.userData.lName,
          password: this.userData.password,
          email: this.userData.email,
        }),
      })
        .then((response) => {
          if (response.status === 200) {
            this.autoLogin();
          }
        });
    },
    submit() {
      if (this.$refs.form.validate()) {
        this.postUserData();
      }
    },
  },
};
</script>

<style>

</style>
