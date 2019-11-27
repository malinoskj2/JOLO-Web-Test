<template>
  <PatientList id="patient-list"/>
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
#patient-list {
  margin-top: 80px;
}
</style>
