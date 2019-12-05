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
          <v-form ref="form" v-model="valid" @submit="submit()" >
              <v-text-field
                v-model="userData.fName"
                label="First Name"
                name="fName"
                type="text"
                @keyup.enter="submit"
                :rules="firstNameRules"
                required
                />
                 <v-text-field
                v-model="userData.lName"
                label="Last Name"
                name="lNamee"
                type="text"
                @keyup.enter="submit"
                :rules="lastNameRules"
                required
                />
                 <v-text-field
                v-model="email"
                label="Email"
                name="Email"
                type="email"
                @keyup.enter="submit"
                :rules="emailRules"
                :error="error"
                :error-messages="errorMessages"
                required
                />
                 <v-text-field
                 v-model="userData.password"
                label="Password"
                name="Password"
                type="password"
                 @keyup.enter="submit"
                :rules="passwordRules"
                required
                />
                <v-text-field
                v-model="userData.retypedPassword"
                label="Confirm Password"
                name="retypePassword"
                type="password"
                @keyup.enter="submit"
                :rules="passwordMatchRules"
                required
                />
            </v-form>
          </v-card-text>
          <v-card-actions >
                <v-spacer />
                <v-flex>
                <v-btn
                :disabled="!valid"
                class="justify-center"
                color="primary"
                form="signUp"
                @click="submit">Submit</v-btn>
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
import debounce from 'lodash/debounce';

const emailCheckDebounceMS = 120;

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
        password: '',
        retypedPassword: '',
      },
      email: '',
      emailTaken: false,
      valid: false,
      error: false,
      errorMessages: '',
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
          successFunction: () => {
            this.$store.dispatch('login', {
              email: this.email,
              ...this.userData,
            })
              .then(() => this.$router.push('/results'))
              .catch(() => console.log('failed to authenticate after successful registration'));
          },
          email: this.email,
          ...this.userData,
        })
        .then(() => console.log('dispatched signup'))
        .catch(() => console.log('failed to dispatch signup'));
    },
    submit() {
      if (this.$refs.form.validate()) {
        this.valid = true;
        this.postUserData();
      }
    },
    showEmailUnavailable() {
      this.error = true;
      this.errorMessages = 'Email already in use.';
    },
    showEmailAvailable() {
      this.error = false;
      this.errorMessages = '';
    },
  },
  watch: {
    email: debounce(function () {
      this.$store.dispatch('emailAvailability', { email: this.email })
        .then((response) => {
          if (response) {
            this.showEmailUnavailable();
          } else {
            this.showEmailAvailable();
          }
        });
    }, emailCheckDebounceMS),
  },
};
</script>

<style>

</style>
