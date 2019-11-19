import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersistence from 'vuex-persist';
import user from '@/store/mod/user';
import test from '@/store/mod/test';
import patient from '@/store/mod/patient';
import router from '@/router';

const vuexLocal = new VuexPersistence({
  storage: window.localStorage,
});

Vue.use(Vuex);

const UNAUTHORIZED = 401;

const handleUnauthorized = (context) => {
  context.commit('signOut');
  router.push('login');
};

const ResponseStatusHandlers = new Map([
  [UNAUTHORIZED, handleUnauthorized],
]);

export default new Vuex.Store({
  modules: { user, test, patient },
  actions: {
    makeAuthenticatedCall(context, payload) {
      context.dispatch(payload.action, payload)
        .then((responseStatus) => {
          console.log(`Made Authenticated Call: ${payload.action}`);

          if (ResponseStatusHandlers.has(responseStatus)) {
            ResponseStatusHandlers.get(responseStatus)(context);
          }
        })
        .catch(() => {
          console.log(`Failed Authenticated Call: ${payload.action}`);

          handleUnauthorized(context);
        });
    },
  },
  plugins: [vuexLocal.plugin],
});
