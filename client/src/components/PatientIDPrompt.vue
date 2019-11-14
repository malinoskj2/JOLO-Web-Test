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
                <v-text-field v-model="patientID" label="Patient ID*" required/>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1"
                 text @click="start"
                 class="text-none">Start</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
export default {
  name: 'PatientIDPrompt',
  data() {
    return {
      patientID: '',
      dialog: true,
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
  },
};
</script>

<style scoped>

</style>
