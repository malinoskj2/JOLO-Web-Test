<template>

  <div id="home">

    <div id="section-a" class="flex-row-center">
      <div class="top-section">
        <h1 class="black-karla">{{topSection.primaryText}}</h1>
        <h2>{{topSection.secondaryText}}</h2>
        <v-btn color="primary" type="submit" @click="$router.push('/signup')">
          Get Started
        </v-btn>
      </div>
      <div class="top-section">
        <img src="@/assets/charts_a.svg" width="500" height="500">
      </div>
    </div>

  </div>

</template>

<script>

export default {
  name: 'home',
  data() {
    return {
      topSection: {
        primaryText: 'Harness the data within.',
        secondaryText: 'Easily administer the JLO test and derive deep, statistical insights from the results',
      },
    };
  },
};
</script>
<style scoped>
  .flex-row-center {
    display: flex;
    flex-direction: row;
    justify-content: center;
  }
  .black-karla {
    color: black;

  }

  .flex-column > * {
    padding: 0 0 2rem 0;
  }
  #section-a {
    padding: 8rem 0 6rem 0;
  }
  h1 {
    line-height: 4rem;
    font-size: 4rem;
    word-wrap: break-word;
    padding-bottom: 1rem;
  }
  h2 {
    color: rgba(0, 0, 0, .8);
    padding-bottom: 1rem;
    max-width: 80%;
  }

  .top-section {
    max-width: 40rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
  }

</style>
