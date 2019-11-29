<template>
  <div id="activity-widget">

    <v-icon large :color="this.isRecording ? 'primary' : 'secondary'"
            :class="{ active: isRecording }">
            {{microphoneSvgPath}}
    </v-icon>

    <p id="status-text" class="font-weight-bold">{{this.statusText}}</p>

  </div>
</template>

<script>
import { mdiMicrophone } from '@mdi/js';

export default {
  name: 'MicrophoneActivityWidget',
  data() {
    return {
      microphoneSvgPath: mdiMicrophone,
    };
  },
  props: {
    isRecording: {
      type: Boolean,
      required: true,
      default: false,
    },
  },
  computed: {
    statusText() {
      return this.isRecording ? 'recording' : 'complete';
    },
  },
};
</script>

<style scoped>
  #activity-widget {
    display: flex;
    flex-direction: column;
  }

  #status-text {
    color: #9575CD;
    font-size: .85rem;
    transform: translateX(-9px);
  }

  .active{
    animation: active-mic 3.2s both infinite;
  }

  @keyframes active-mic {
    0% {
      transform: scale3d(1, 1, 1);
    }
    10% {
      transform: scale3d(1.25, 0.75, 1);
    }
    15% {
      transform: scale3d(0.75, 1.25, 1);
    }
    20% {
      transform: scale3d(1.15, 0.85, 1);
    }
    30% {
      transform: scale3d(1, 1, 1);
    }
    40% {
      transform: scale3d(1, 1, 1);
    }
    50% {
      transform: scale3d(1, 1, 1);
    }
    100% {
      transform: scale3d(1, 1, 1);
    }
  }

</style>
