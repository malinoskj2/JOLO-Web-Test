import polyfill from 'audio-recorder-polyfill';

export default class Recorder {
  constructor() {
    window.MediaRecorder = polyfill;
    this.recorder = null;
    this.recordings = [];
    navigator.mediaDevices.getUserMedia({ audio: true })
      .then((stream) => {
        this.recorder = new MediaRecorder(stream);
        this.recorder.addEventListener('dataavailable', (e) => {
          this.recordings.push(e.data);
        });
      },
      // eslint-disable-next-line no-unused-vars
      (error) => {});
  }

  start() {
    this.recorder.start();
  }

  stop() {
    this.recorder.stop();
  }

  lastRecording() {
    return this.recordings.pop();
  }
}
