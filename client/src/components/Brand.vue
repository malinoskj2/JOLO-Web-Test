<template>
  <div id="brand">
    <div id="name">{{name}}</div>
    <div id="delimiter">{{delimiter}}</div>
  </div>
</template>

<script>
export default {
  name: 'Brand',
  props: {
    name: {
      type: String,
      required: true,
      default: 'Missing Brand Name',
    },
    delimiter: {
      type: String,
      required: false,
      default: '|',
    },
  },
};
</script>

<style scoped>
  #brand {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    flex-wrap: nowrap;
  }
  #name {
    font-size: 1.25rem;
    font-weight: bold;
    color: #000000;
    font-family: 'Karla', sans-serif;
    padding: 0 .2rem 0 0rem;
  }
  #delimiter {
    color: #E85B46;
    font-size: 1.5rem;
    transform: translateY(-1px);
  }
</style>
