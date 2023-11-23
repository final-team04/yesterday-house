import VueDatePicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";

import App from "./App.vue";
import router from "./router";
import PerfectScrollbar from "vue3-perfect-scrollbar";
import "vue3-perfect-scrollbar/dist/vue3-perfect-scrollbar.css";
import VCalendar from "v-calendar";
import "v-calendar/style.css";

import { createApp } from "vue";
import { createPinia } from "pinia";
import { createPersistedState } from "pinia-plugin-persistedstate";

import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "./assets/scss/global.scss";
import "./index.css";

import BaseCard from "./components/base/BaseCard.vue";
import BaseBtn from "./components/base/BaseBtn.vue";

// perfectscrollbar plugins
import "vue3-perfect-scrollbar/dist/vue3-perfect-scrollbar.css";
const vuetify = createVuetify({
  components,
  directives,
  theme: { disable: true },
});

const app = createApp(App);

const pinia = createPinia();
pinia.use(
  createPersistedState({
    storage: sessionStorage,
  })
);

// app.component("BaseCard", BaseCard);
// app.component("BaseBtn", BaseBtn);
app.use(PerfectScrollbar);
app.use(pinia);
app.use(vuetify);
// app.use(VCalendar);
app.use(router);

app.component("VueDatePicker", VueDatePicker);

app.mount("#app");
