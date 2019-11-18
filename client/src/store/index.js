import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersistence from 'vuex-persist';
import user from '@/store/mod/user';
import test from '@/store/mod/test';
import patient from '@/store/mod/patient';

const vuexLocal = new VuexPersistence({
  storage: window.localStorage,
});

Vue.use(Vuex);

export default new Vuex.Store({
  modules: { user, test, patient },
  plugins: [vuexLocal.plugin],
});
