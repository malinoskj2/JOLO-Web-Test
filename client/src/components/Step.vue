<template>
  <div id="step">
    <div id="text-section">
      <div id="title">{{title}}</div>
      <div id="description">{{description}}</div>
    </div>
    <slot/>
  </div>
</template>

<script>
export default {
  name: 'Step',
  props: {
    leftSideImage: {
      type: Boolean,
      required: false,
      default: true,
    },
    title: {
      type: String,
      required: true,
      default: 'Missing Step Title',
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
  #step {
    margin: 2rem 2rem 2rem 2rem;
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
  }

  #text-section {
    display: flex;
    flex-direction: column;
    justify-content: center;

    text-align: left;
  }

  #title {
    font-family: 'Karla', sans-serif;
    font-size: 2rem;
    color: black;

    padding-bottom: .5rem;
  }

  #description {
    text-align: center;
    color: black;
    font-family: 'Lato', sans-serif;
  }


</style>
