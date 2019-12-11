<template>

      <div id="exam" class="mx-auto">
        <patient-i-d-prompt @set-patient="start"/>

        <v-dialog v-model="exitGuard" persistent max-width="290">
          <v-card>
            <v-card-title class="headline">Quitting The Exam?</v-card-title>
            <v-card-text>Only completed trials will be recorded.</v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" text @click="exitGuard = false">No</v-btn>
              <v-btn color="primary" text @click="exitExam">Yes</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>

        <div id="exam-nav" class="d-flex justify-space-around">
          <v-icon @click="previousTrial"
                  :color="this.hasPreviousQuestion ? 'primary' : 'secondary'"
                  x-large class="exam-nav-arrow">
            {{leftArrowSvgPath}}
          </v-icon>
          <div @click="stopRecording">
            <MicrophoneActivityWidget :is-recording="isRecording"
                                      class="align-self-center"/>
          </div>

          <v-icon @click="nextTrial"
                  :color="this.hasNextQuestion ? 'primary' : 'secondary'"
                  x-large class="exam-nav-arrow">
            {{rightArrowSvgPath}}
          </v-icon>
        </div>

        <v-card id="drawing-card"  :style="drawingScale">
          <p id="trial-count">
            Trial {{this.currentTrialNumber}} of {{this.numQuestions}}
          </p>
          <DrawTest id="drawing-test"
                    :question="this.currentQuestion"
          :canvas-width="canvas.width"
          :canvas-height="canvas.height"/>
        </v-card>


        <v-spacer></v-spacer>
      </div>

</template>

<script>
import { mapGetters, mapMutations } from 'vuex';
import { mdiArrowLeftCircle, mdiArrowRightCircle, mdiMicrophone } from '@mdi/js';
import polyfill from 'audio-recorder-polyfill';
import DrawTest from '@/components/DrawTest.vue';
import PatientIDPrompt from '@/components/PatientIDPrompt.vue';
import MicrophoneActivityWidget from '../components/MicrophoneActivityWidget.vue';

export default {
  name: 'exam',
  // eslint-disable-next-line no-unused-vars
  beforeRouteLeave(to, from, next) {
    if (this.inProgress && !this.onLastQuestion) {
      this.next = next;
      this.exitGuard = true;
    } else {
      next();
    }
  },
  mounted() {
    // setup recorder
    // answer will be submitted async over http when audio binary becomes available
    window.MediaRecorder = polyfill;
    navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
      this.recorder = new MediaRecorder(stream);
      this.recorder.addEventListener('dataavailable', (e) => {
        this.$store.dispatch('makeAuthenticatedCall',
          {
            action: 'submitRecord',
            recording: e.data,
            testSubmissionID: this.testSubmissionID,
            questionID: this.answeredQuestions.shift(),
          })
          .then(() => console.log('dispatched submitRecord'))
          .catch(() => console.log('failed to dispatch submitRecord'));
      });
    });
  },
  components: {
    DrawTest,
    PatientIDPrompt,
    MicrophoneActivityWidget,
  },
  data() {
    return {
      recorder: {},
      isRecording: true,
      exitGuard: false,
      leftArrowSvgPath: mdiArrowLeftCircle,
      rightArrowSvgPath: mdiArrowRightCircle,
      microphoneSvgPath: mdiMicrophone,
      next: {},
      answeredQuestions: [],
      canvas: {
        width: 613,
        height: 797,
      },
    };
  },
  methods: {
    exitExam() {
      this.resetTest();
      this.next();
    },
    start(payload) {
      this.$store.dispatch('makeAuthenticatedCall', {
        action: 'fetchTest',
        patientID: payload.patientID,
      })
        .then(() => console.log('fetched test'))
        .catch(() => console.log('error fetching test'));
      this.$store.commit('setInProgress');
      this.recorder.start();
    },
    stopRecording() {
      console.log('Stopping Recording.');
      this.answeredQuestions.push(this.currentQuestion.questionID);
      this.recorder.stop();
      this.isRecording = false;
    },
    nextTrial() {
      if (this.recorder.state === 'recording') {
        this.stopRecording();
      }
      if (this.onLastQuestion) {
        setTimeout(() => {
          this.$router.push('/results');
          this.resetTest();
        }, 4000);
      } else {
        this.incrementActiveQuestion();
        this.recorder.start();
        this.isRecording = true;
      }
    },
    previousTrial() {
      if (this.recorder.state === 'recording') {
        this.stopRecording();
      }

      this.decrementActiveQuestion();
      this.recorder.start();
      this.isRecording = true;
    },
    ...mapMutations([
      'incrementActiveQuestion',
      'decrementActiveQuestion',
      'resetTest',
      'setQuestionAnswered',
    ]),
  },
  computed: {
    drawingScale() {
      const preDrawingHeight = 230;
      const heightAvailable = document.body.scrollHeight - preDrawingHeight;
      const scaleAmount = (heightAvailable / this.canvas.height).toFixed(2);
      console.log(`scaleAmount: ${scaleAmount}`);

      return {
        transform: `scale(${scaleAmount})`,
        'transform-origin': 'top center',
      };
    },
    ...mapGetters([
      'test',
      'inProgress',
      'currentQuestion',
      'hasPreviousQuestion',
      'hasNextQuestion',
      'currentTrialNumber',
      'numQuestions',
      'testSubmissionID',
      'onLastQuestion',
    ]),
  },
};
</script>

<style>
#exam {
  margin-top: 80px;
}

#drawing-card {
  margin-top: 2px;
  filter: drop-shadow(6px 4px 3px rgba(53, 42, 85, 0.68));
  transform: scale(.95);
}

#trial-count {
  margin: 0rem .5rem 0 0;
  padding-top: .2rem;
  font-size: 1rem;
  filter: opacity(.3);
  text-align: right;
}

#drawing-test {
  margin-top: -24px;
}

.exam-nav-arrow {
  filter: drop-shadow(0px 1px 3px rgba(53, 42, 85, 0.68));
}

.exam-nav-arrow:hover {
  filter: drop-shadow(0px 0px 1px #000000);
}
</style>
