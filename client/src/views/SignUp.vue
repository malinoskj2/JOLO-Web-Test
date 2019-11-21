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
                :rules="firstNameRules"
                required
                />
                 <v-text-field
                v-model="userData.lName"
                label="Last Name"
                name="lNamee"
                type="text"
                :rules="lastNameRules"
                required
                />
                 <v-text-field
                v-model="userData.email"
                label="Email"
                name="Email"
                type="email"
                :rules="emailRules"
                required
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
                label="Confirm Password"
                name="retypePassword"
                type="password"
                :rules="passwordMatchRules"
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
  metaInfo() {
    return {
      title: 'Sign Up',
    };
  },
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
        v => /(?=.*[a-z])/.test(v) || 'Password must have atleast one lowercase character.',
        v => /(?=.*[A-Z])/.test(v) || 'Password must have atleast one uppercase character.',
        v => /(?=.*[0-9])/.test(v) || 'Password must contain atleast one numeric character.',
        v => /(?=.[!@#$%^&])/.test(v) || 'Password must contain atleast a one special character. (! @ # $ % ^ &)',
      ],
      passwordMatchRules: [
        v => !!v || 'Please retype your password.',
        v => (v && v === this.userData.password) || 'Does not match password.',
      ],
    };
  },
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
