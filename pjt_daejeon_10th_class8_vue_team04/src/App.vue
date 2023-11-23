<script setup>
import { onErrorCaptured, onMounted } from "vue";

import { storeToRefs } from "pinia";
import { useError, useUserStore } from "./stores/counter";

import HeaderComponent from "./components/HeaderComponent.vue";
import ErrorComponent from "./components/ErrorComponent.vue";
import ToastComponent from "./components/ToastComponent.vue";
import router from "./router";

import { getUserInfo } from "./api/User";
/*
클릭 시 퍼지는 효과를 지우고 싶다면 v-ripple.stop을 태그 옵션에 추가하면 된다.
*/

const { error, modal } = storeToRefs(useError());
const user = storeToRefs(useUserStore()).user;

onMounted(async () => {
  const userInfo = await getUserInfo();
  user.value = userInfo.data || null;
});

onErrorCaptured((err) => {
  console.log(err);
  error.value =
    err.name === "AxiosError"
      ? err.response
        ? typeof err.response.data === "string"
          ? err.response.data
          : "권한이 없습니다."
        : "일시적인 오류입니다. 잠시 후에 다시 시도해주세요."
      : err.message;
  modal.value = true;
  return false;
});
</script>

<template>
  <v-app>
    <ErrorComponent />
    <ToastComponent />
    <HeaderComponent />
    <RouterView />
  </v-app>
</template>

<style>
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 400;
  font-style: normal;
}
* {
  font-family: "Pretendard-Regular";
  font-weight: 600;
}

*::-webkit-scrollbar {
  display: none;
}
.v-btn {
  background-color: black;
  color: white;
}

.v-application__wrap {
  height: 100vh;
  background-position: center;
  background-image: url("/images/background.jpg");
  background-size: cover;
}
</style>
