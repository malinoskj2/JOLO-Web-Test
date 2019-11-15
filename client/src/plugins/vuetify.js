import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import colors from 'vuetify/lib/util/colors';
// eslint-disable-next-line import/no-extraneous-dependencies

Vue.use(Vuetify);

export default new Vuetify({
  icons: {
    iconfont: 'mdiSvg',
  },
  theme: {
    themes: {
      light: {
        primary: colors.deepPurple.lighten2,
        secondary: colors.grey.lighten3,
        // accent: colors.shades.black,
        // error: colors.red.accent3,
      },
    },
  },
});
