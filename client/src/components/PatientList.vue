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
      ],
      patientID: [
        { ID: 3 },
      ],
      patientData: [{ ID: '1232313123' }, '4324234124', '3424123412', '341123421'],
    };
  },
  methods: {
  },
};
</script>

<style>

</style>
