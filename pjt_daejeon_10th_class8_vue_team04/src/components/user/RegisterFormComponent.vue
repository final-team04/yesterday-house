<script setup>
/*************** imports ***************/
import { ref } from "vue";

// routing
import { useRouter } from "vue-router";

// form rules
import { userIdRule, userNameRule, userPwRule, userPwCheckRule } from "../../util/Rules";

// use global states
import { storeToRefs } from "pinia";
import { useMessage } from "../../stores/counter";

// requests
import { register } from "../../api/User";

/*************** declaration ***************/
// router
const router = useRouter();

// global states
const { message, toast } = storeToRefs(useMessage());

// local states
const userId = ref("");
const userName = ref("");
const userPw = ref("");
const userPwCheck = ref("");
const form = ref();

/*************** functions ***************/
const onRegisterSubmit = async () => {
  // const { valid } = await form.value.validate();

  // if (!valid) throw new Error("입력이 양식에 맞지 않습니다.");

  await register({
    userId: userId.value,
    userPw: userPw.value,
    userName: userName.value,
  });

  // console.log("회원가입됨");

  message.value = "회원가입 되었습니다. 다시 로그인해 주세요.";
  toast.value = true;

  router.push("/login");
};
</script>

<template>
  <div
    class="m-auto w-screen max-w-md overflow-hidden rounded-3xl bg-white text-sm leading-6 shadow-lg ring-1 ring-gray-900/5"
  >
    <div class="flex flex-1 flex-col justify-center px-6 py-12 lg:px-8">
      <div class="sm:mx-auto sm:w-full sm:max-w-sm">
        <h1 class="mt-10 text-center text-3xl font-bold leading-9 tracking-tight text-gray-900">
          가입 후 모든 기능을 사용하세요.
        </h1>
      </div>

      <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form class="space-y-6" @submit.prevent="onRegisterSubmit" ref="form">
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
            <label for="email" class="block text-sm font-medium leading-6 text-gray-900"
              >닉네임</label
            >
            <div class="mt-2">
              <input
                v-model="userName"
                id="userName"
                name="userName"
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
            <div class="flex items-center justify-between">
              <label for="password" class="block text-sm font-medium leading-6 text-gray-900"
                >비밀번호 확인</label
              >
            </div>
            <div class="mt-2">
              <input
                v-model="userPwCheck"
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
              회원가입
            </button>
          </div>
        </form>

        <p class="mt-10 text-center text-sm text-gray-500">
          이미 회원인가요?
          {{ " " }}
          <router-link
            to="/login"
            class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500"
            >로그인하기</router-link
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
      <v-form fast-fail @submit.prevent="onRegisterSubmit" validate-on="input submit" ref="form">
        <v-text-field
          v-model="userId"
          label="아이디"
          placeholder="아이디"
          required
          :rules="userIdRule"
          type="text"
        ></v-text-field>
        <v-text-field
          v-model="userName"
          label="닉네임"
          placeholder="닉네임"
          required
          :rules="userNameRule"
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
        <v-text-field
          v-model="userPwCheck"
          label="비밀번호 확인"
          placeholder="비밀번호 확인"
          required
          :rules="[
            ...userPwCheckRule,
            (userPwCheck) => userPwCheck === userPw || `비밀번호가 일치하지 않습니다.`,
          ]"
          type="password"
        ></v-text-field>
        <v-btn type="submit" block class="mt-2">회원가입</v-btn>
      </v-form>
    </v-sheet>
  </v-layout>
</template>

<style scoped></style>

-->
