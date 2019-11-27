<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="patients"
      :items-per-page="itemsPerPage"
      class="elevation-1">

      <template v-slot:item.download="{ item }">

          <v-icon @click="downloadSpreadSheet(item.ID)">
            {{fileDownloadSvgPath}}
          </v-icon>
      </template>
    </v-data-table>
  </v-container>

</template>

<script>
import { mdiFileDownload } from '@mdi/js';

export default {
  name: 'GetResults',
  data() {
    return {
      fileDownloadSvgPath: mdiFileDownload,
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
      itemsPerPage: 15,
    };
  },
  computed: {
    patients() {
      return this.$store.getters.patients
        .map(patient => ({ download: this.fileDownloadSvgPath, ...patient }));
    },
  },
  methods: {
    downloadSpreadSheet(patientID) {
      fetch(`${process.env.VUE_APP_API}/patient/spreadsheet?patientID=${patientID}`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${this.$store.getters.token}`,
        },
      }).then(resp => resp.blob())
        .then((blob) => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.style.display = 'none';
          a.href = url;
          // the filename you want
          a.download = 'todo-1.xls';
          document.body.appendChild(a);
          a.click();
          window.URL.revokeObjectURL(url);
        })
        .catch(() => console.log('Could not download the spreadsheet.'));
    },
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
