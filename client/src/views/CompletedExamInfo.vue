<template>
<div class="completed-exam-info">
    <v-container fluid>
      <v-layout row class="mx-1">
        <v-col>
          <div class="d-flex">
            <div class="d-flex align-center">
              <h3 class="patient-heading-title">Patient:</h3>
              <h3 class="patient-heading-val mx-1">{{this.patientId}}</h3>
            </div>
            <div class="d-flex align-center">
              <v-divider class="mx-4"
                         vertical color="primary"
                         style="display: inline;">
              </v-divider>
            </div>
            <div class="d-flex align-center mx-1">
              <v-icon medium color="primary">{{this.spreadsheetSvgPath}}</v-icon>
            </div>
          </div>
        </v-col>
        <v-col>
          <div class="d-flex justify-end">
            <h3 class="patient-heading-title">Trials:</h3>
            <h3 class="patient-heading-val mx-1">{{this.numTrials}}</h3>
          </div>
        </v-col>
      </v-layout>
      <v-layout row>
        <v-divider class="mx-4"
                   color="primary"
                   style="display: inline;">
        </v-divider>
      </v-layout>

        <v-simple-table fixed-header height="300px" id="trial-table">
          <template v-slot:default>
            <thead>
            <tr>
              <th class="text-left trial-table-head">Trial</th>
              <th class="text-left trial-table-head">Audio</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="trial in trials" :key="trial.id">
              <td class="trial-position-number">{{ trial.position }}</td>
              <td>
                <div>
                  <v-icon medium color="primary">{{audiobookSvgPath}}</v-icon>
                  <v-icon medium color="primary">{{playSvgPath}}</v-icon>
                </div>
              </td>
            </tr>
            </tbody>
          </template>
        </v-simple-table>
    </v-container>

</div>

</template>

<script>
import { mdiGoogleSpreadsheet, mdiAudiobook, mdiPlay } from '@mdi/js';

export default {
  name: 'CompletedExamInfo',
  data() {
    return {
      numTrials: 30,
      patientId: 9000,
      spreadsheetSvgPath: mdiGoogleSpreadsheet,
      audiobookSvgPath: mdiAudiobook,
      playSvgPath: mdiPlay,
      trials: [
        { position: 0, id: 0 },
        { position: 1, id: 1 },
        { position: 2, id: 2 },
      ],
    };
  },
};
</script>

<style scoped>
  .bordered {
    border: solid 1px black;
  }

.completed-exam-info {
  margin-top: 56px;
}
  .patient-heading-title {
    font-weight: 500;
  }

  .patient-heading-subtitle {
    font-weight: 400;
  }

  .patient-heading-val {
    color: #9575CD;
  }
  .test .theme--light.v-table { background-color: #00f; }

  #trial-table {
    background-color: transparent;
  }

  .trial-table-head {
    background-color: transparent !important;
  }

  .trial-position-number {
    color: #9575CD;
    font-weight: 600;
  }

</style>
