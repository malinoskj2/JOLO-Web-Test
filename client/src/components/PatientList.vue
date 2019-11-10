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
      patientData: [
        { ID: '100' },
        { ID: '200' },
        { ID: '300' },
        { ID: '400' },
        { ID: '500' },
        { ID: '600' },
      ],
    };
  },
  methods: {
  },
};
</script>

<style>

</style>
