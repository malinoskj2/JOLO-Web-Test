import polyfill from 'audio-recorder-polyfill';

export default class Recorder {
  constructor() {
    window.MediaRecorder = polyfill;
    this.recorder = {};
    this.recording = {};

    navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
      this.recorder = new MediaRecorder(stream);
      this.recorder.addEventListener('dataavailable', (e) => {
        this.recording = e.data;
      });
    });
  }

  start() {
    this.recorder.start();
  }

  stop() {
    this.recorder.stop();
  }

  getLastRecording() {
    return this.recording;
  }
}
