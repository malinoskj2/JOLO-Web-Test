<template>
  <v-container>
    <v-data-table
      :headers="headers"
      item-key="ID"
      :items="patients"
      :items-per-page="itemsPerPage"
      show-expand
      single-expand
      single-select
      class="elevation-1">

      <template v-slot:item.download="{ item }">
          <v-icon @click="downloadSpreadSheet(item.ID)">
            {{fileDownloadSvgPath}}
          </v-icon>
      </template>
      <template v-slot:expanded-item="{ item }">
        <div v-for="(trial,index) in item.trials"
             :key="index" class="trial-item">
          <div @click="downloadTrial(trial)" class="trial-item-text"
              :key="index">Trial {{trial.questionID}}
            <v-icon size="18px" class="trial-item-icon"> {{downloadSvgPath}}</v-icon>
          </div>
        </div>

      </template>
    </v-data-table>
  </v-container>

</template>

<script>
import { mdiFileDownload, mdiDownload } from '@mdi/js';

export default {
  name: 'GetResults',
  data() {
    return {
      fileDownloadSvgPath: mdiFileDownload,
      downloadSvgPath: mdiDownload,
      headers: [
        {
          text: 'Patient ID',
          align: 'center',
          sortable: false,
          value: 'ID',
        },
        {
          text: 'Download',
          align: 'center',
          sortable: false,
          value: 'download',
        },
        { text: '', value: 'data-table-expand' },
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
          a.download = `patient-${patientID}.xls`;
          document.body.appendChild(a);
          a.click();
          window.URL.revokeObjectURL(url);
        })
        .catch(() => console.log('Could not download the spreadsheet.'));
    },
    downloadTrial(trial) {
      fetch(`${process.env.VUE_APP_API}/test/audio?answerAttemptID=${trial.answerAttemptID}`, {
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
          a.download = `patient${trial.patientID}-trial${trial.questionID}.wav`;
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
.trial-item {
  color: #757575 !important;
  font-weight: 500;
  font-size: 1rem;
  padding-top: .20rem;
  padding-left: .25rem;
}
.trial-item:hover {
  color: #9575CD !important;
  cursor: pointer;
  transform: scale(1.05) translateX(1rem);
}

.trial-item:hover > * > * {
  color: #9575CD !important;
}

.trial-item-text {
  border: none;
}

.trial-divider {
  transform: scaleX(4);
}
</style>
