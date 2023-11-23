<script setup>
/*************** imports ***************/
import { ref } from "vue";

// routing
import { useRouter } from "vue-router";

// form rules
import { userIdRule, userPwRule } from "../../util/Rules";

// use global state
import { storeToRefs } from "pinia";
import { useMessage } from "../../stores/counter";
import { useUserStore } from "../../stores/counter";

// requests
import { login } from "../../api/User.js";

/*************** declaration ***************/
// router
const router = useRouter();

// global states
const user = storeToRefs(useUserStore()).user;
const { message, toast } = storeToRefs(useMessage());

// local states
const userId = ref("");
const userPw = ref("");
const form = ref();

/*************** functions ***************/

const onLoginSubmit = async () => {
  // const { valid } = await form.value.validate();

  // if (!valid) throw new Error("입력이 양식에 맞지 않습니다.");

  const response = await login({ userId: userId.value, userPw: userPw.value });

  // 유저 상태 저장
  user.value = {
    userId: response.data.userId,
    userName: response.data.userName,
  };

  message.value = "로그인 되었습니다.";
  toast.value = true;

  router.push("/");
};
</script>

<template>
  <!--
    This example requires updating your template:

    ```
    <html class="h-full bg-white">
    <body class="h-full">
    ```
  -->
  <div
    class="m-auto w-screen max-w-md overflow-hidden rounded-3xl bg-white text-sm leading-6 shadow-lg ring-1 ring-gray-900/5"
  >
    <div class="flex flex-1 flex-col justify-center px-6 py-12 lg:px-8">
      <div class="sm:mx-auto sm:w-full sm:max-w-sm">
        <!-- <img
        class="mx-auto h-10 w-auto"
        src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
        alt="Your Company"
      /> -->
        <h1 class="mt-10 text-center text-3xl font-bold leading-9 tracking-tight text-gray-900">
          계정에 로그인 하세요.
        </h1>
      </div>

      <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form class="space-y-6" @submit.prevent="onLoginSubmit" ref="form">
          <div>
            <label for="email" class="block text-sm font-medium leading-6 text-gray-900"
              >아이디</label
            >
            <div class="mt-2">
              <input
                v-model="userId"
                id="userId"
                name="userId"
                type="text"
                required
                class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div>
            <div class="flex items-center justify-between">
              <label for="password" class="block text-sm font-medium leading-6 text-gray-900"
                >비밀번호</label
              >
            </div>
            <div class="mt-2">
              <input
                v-model="userPw"
                id="userPw"
                name="userPw"
                type="password"
                required
                class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div>
            <button
              type="submit"
              class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              로그인
            </button>
          </div>
        </form>

        <p class="mt-10 text-center text-sm text-gray-500">
          회원이 아닌가요?
          {{ " " }}
          <router-link
            to="/register"
            class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500"
            >회원가입하기</router-link
          >
        </p>
      </div>
    </div>
  </div>
</template>

<!-- 
<template>
  <v-layout class="align-center mx-auto">
    <v-sheet width="600">
      <v-form fast-fail @submit.prevent="onLoginSubmit" validate-on="input submit lazy" ref="form">
        <v-text-field
          v-model="userId"
          label="아이디"
          placeholder="아이디"
          required
          :rules="userIdRule"
          type="text"
        ></v-text-field>
        <v-text-field
          v-model="userPw"
          label="비밀번호"
          placeholder="비밀번호"
          required
          :rules="userPwRule"
          type="password"
        ></v-text-field>
        <v-btn type="submit" block class="mt-2">로그인</v-btn>
      </v-form>
    </v-sheet>
  </v-layout>
</template>

<style scoped></style>
 -->
