import { ref } from "vue";
import { defineStore } from "pinia";

export const useUserStore = defineStore(
  "userInfo",
  () => {
    const user = ref(null);

    return { user };
  },
  {
    persist: true,
  },
);

export const useError = defineStore("error", () => {
  const error = ref("");
  const modal = ref(false);
  return { error, modal };
});

export const useMessage = defineStore("message", () => {
  const message = ref("");
  const toast = ref(false);
  return { message, toast };
});

export const useMap = defineStore("map", () => {
  const created = ref(false);

  return { created };
});

export const useAptInfo = defineStore("aptInfo", () => {
  const aptList = ref(null);
  const inCircleList = ref(null);
  const inCircleInfo = ref(null);
  const pickAptData = ref(null);

  return { aptList, inCircleList, pickAptData, inCircleInfo };
});
