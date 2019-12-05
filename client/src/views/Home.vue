<template>

    <div id="home">

      <div id="section-a" class="flex-row-center">
        <div class="top-section">
          <h1 class="black-karla">{{topSection.primaryText}}</h1>
          <h2>{{topSection.secondaryText}}</h2>
          <v-btn color="primary"
                 type="submit"
                 class="text-none"
                 @click="$router.push('/signup')">
            Get Started
          </v-btn>
        </div>
        <div class="top-section">
          <img src="@/assets/charts_a.svg" width="500" height="500">
        </div>
      </div>

      <v-footer absolute class="font-weight-medium">

          <v-col class="text-center" cols="12">
            <div class="flex-row-center">
              <v-icon class="footer-icon" @click="navToGithub()">
                {{this.githubSvgPath}}
              </v-icon>
              <v-icon class="footer-icon" @click="navToUniversity()">
                {{this.schoolSvgPath}}
              </v-icon>
            </div>
          </v-col>


      </v-footer>
    </div>

</template>

<script>
import { mdiGithubCircle, mdiSchool } from '@mdi/js';

export default {
  name: 'home',
  data() {
    return {
      topSection: {
        primaryText: 'Harness the data within.',
        secondaryText: 'Easily administer the JLO test and derive deep, statistical insights from the results',
      },
      githubSvgPath: mdiGithubCircle,
      schoolSvgPath: mdiSchool,
    };
  },
  methods: {
    navToGithub() {
      window.location.href = process.env.VUE_APP_GITHUB;
    },
    navToUniversity() {
      window.location.href = process.env.VUE_APP_UNIVERSITY;
    },
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
    background-color: #FAFAFA;
    padding: 8rem 0 6rem 0;
  }
  #home { }
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

  .footer-icon {
    margin: 1rem;
  }
</style>
