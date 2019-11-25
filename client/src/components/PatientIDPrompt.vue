<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>

        <v-card-title>
          <span class="headline">PatientID</span>
        </v-card-title>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="12" md="12">
                <v-text-field v-model="patientID"
                              :rules="rules"
                              :error="error"
                              :error-messages="errorMessages"
                              label="Patient ID*" required/>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1"
                 text @click="start"
                 :disabled="this.error"
                 class="text-none">Start</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import debounce from 'lodash/debounce';

const idCheckDebounceMS = 120;

export default {
  name: 'PatientIDPrompt',
  data() {
    return {
      patientID: '',
      dialog: true,
      rules: [
        value => !!value || 'Required.',
      ],
      errorMessages: '',
      error: false,
    };
  },
  methods: {
    start() {
      this.dialog = false;
      this.setPatient();
    },
    setPatient() {
      this.$emit('set-patient', {
        patientID: this.patientID,
      });
    },
    showIdUnavailable() {
      this.error = true;
      this.errorMessages = 'Patient ID already in use.';
    },
    showIdAvailable() {
      this.error = false;
      this.errorMessages = '';
    },
  },
  watch: {
    // eslint-disable-next-line
    patientID: debounce(function () {
      this.$store.dispatch('isIdAvailable', { patientID: this.patientID })
        .then((isAvailable) => {
          if (isAvailable) {
            this.showIdAvailable();
          } else {
            this.showIdUnavailable();
          }
        });
    }, idCheckDebounceMS),
  },

};
</script>

<style scoped>

</style>
