<template>
  <div id="feature">
    <div id="feature-image">
      <slot/>
    </div>
    <div id="title">{{title}}</div>
    <div id="description">{{description}}</div>
  </div>
</template>

<script>
export default {
  name: 'Feature',
  props: {
    title: {
      type: String,
      required: true,
      default: 'Missing Feature Title',
    },
    description: {
      type: String,
      required: true,
      default: 'Missing Feature Description',
    },
  },
};
</script>

<style scoped>
  #feature {
    text-align: center;
  }
  #feature-image {
    padding-bottom: 1rem;
  }
  #title {
    font-size: 1rem;
    color: black;
    padding-bottom: .5rem;
  }
  #description {
    text-align: center;
    color: black;
  }
</style>
