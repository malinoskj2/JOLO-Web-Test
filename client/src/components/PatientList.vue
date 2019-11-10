<template>
  <div>
    <v-data-table
    :headers="headers"
    :items="patientData"
    :items-per-page="5"
    class="elevation-1"
  ></v-data-table>
  </div>
</template>

<script>
export default {
  name: 'GetResults',
  data() {
    return {
      headers: [
        {
          text: 'Patient ID',
          align: 'center',
          sortable: false,
          value: 'ID',
        },
        {
          text: 'Download',
          align: 'right',
          sortable: false,
          value: 'download',
        },
      ],
      patientData: [],
    };
  },
  methods: {
    async fetchPatients() {
      console.log(`Token: ${this.$store.state.token}`);
      const response = await fetch('http://localhost:8081/patient/all', {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${this.$store.state.token}`,
        },
      });
      const json = await response.json();
      this.patientData = json.patients.map(patientID => ({ ID: patientID }));
    },
  },
  mounted() {
    this.fetchPatients().then(() => console.log('patients were fetched'));
  },
};
</script>

<style>

</style>
