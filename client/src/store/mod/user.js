export default {
  state: {
    token: '',
    email: '',
    firstName: '',
    lastName: '',
  },
  mutations: {
    saveToken(state, token) {
      state.token = token;
    },
    saveEmail(state, email) {
      state.email = email;
    },
    saveFirstName(state, firstName) {
      state.firstName = firstName;
    },
    saveLastName(state, lastName) {
      state.lastName = lastName;
    },
    signOut(state) {
      state.email = null;
      state.token = null;
    },
  },
  getters: {
    token: state => state.token,
    email: state => state.email,
    firstName: state => state.firstName,
    lastName: state => state.lastName,
    isAuthenticated: state => Boolean(state.token),
  },
  actions: {
    async login(context, payload) {
      const response = await fetch(`${process.env.VUE_APP_API}/auth/authenticate`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: payload.email,
          password: payload.password,
        }),
      });

      const json = await response.json();
      context.commit('saveEmail', json.email);
      context.commit('saveToken', json.token);
      context.commit('saveFirstName', json.firstName);
      context.commit('saveLastName', json.lastName);
    },
    async signup(context, payload) {
      const response = await fetch(`${process.env.VUE_APP_API}/auth/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          fName: payload.fName,
          lName: payload.lName,
          password: payload.password,
          email: payload.email,
        }),
      });

      if (response.ok) { payload.successFunction(); }
    },
    async emailAvailability(context, payload) {
      const response = await fetch(`${process.env.VUE_APP_API}/auth/existsEmail`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: payload.email,
        }),
      });

      const isEmailTaken = await response.json();
      return isEmailTaken;
    },
  },
};
