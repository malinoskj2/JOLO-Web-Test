<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="$store.getters.patients"
      :items-per-page="15"
      class="elevation-1"/>
  </v-container>

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
  mounted() {
    this.$store.dispatch('makeAuthenticatedCall', {
      action: 'fetchPatients',
    })
      .then(() => console.log('fetched patients successfully.'))
      .catch(() => console.log('failed to fetch patients'));
  },
};
</script>

<style>

</style>
