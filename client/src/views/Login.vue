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
                <v-btn class="justify-center" color="primary"
                       @click="$store.dispatch('login', loginCred)">Submit</v-btn>
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
};
</script>

<style>

</style>
