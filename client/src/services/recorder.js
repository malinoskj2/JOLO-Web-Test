import polyfill from 'audio-recorder-polyfill';

export default class Recorder {
    constructor() {
        window.MediaRecorder = polyfill
        this.recorder = null
        this.recordings = []

        navigator.mediaDevices.getUserMedia({audio: true})
            .then(stream => {
                    console.log('Obtained audio stream')
                    this.recorder = new MediaRecorder(stream)
                    this.recorder.addEventListener('dataavailable', e => {
                        this.recordings.push(e.data);
                    })
                },
                error => console.log(`Failed to obtain audio stream: ${error}`))
    }

    start() {
        console.log('Recording Audio.')
        this.recorder.start()
    }

    stop() {
        console.log('Stopped Recording.')
        this.recorder.stop()
        console.log(this.recordings)
    }

    lastRecording() {
        return this.recordings.pop()
    }
}
