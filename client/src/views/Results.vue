<template>
  <PatientList class="my-auto" />
</template>

<script>
import PatientList from '@/components/PatientList.vue';

export default {
  name: 'results',
  metaInfo() {
    return {
      title: 'Results',
    };
  },
  components: {
    PatientList,
  },
};
</script>

<style>

</style>
