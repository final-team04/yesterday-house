<script setup>
/*************** imports ***************/
import { ref } from "vue";

// routing
import { useRouter } from "vue-router";

// form rules
import { userNameRule, userPwRule, userPwCheckRule } from "../../util/Rules";

// use global state
import { storeToRefs } from "pinia";
import { useMessage } from "../../stores/counter";
import { useUserStore } from "../../stores/counter";

// requests
import { edit } from "../../api/User.js";

/*************** declaration ***************/
// router
const router = useRouter();

// global states
const { user } = storeToRefs(useUserStore());
console.log("userinfo at editform : ", user.value);
const { message, toast } = storeToRefs(useMessage());

// local states
const newUserName = ref("");
const oldUserPw = ref("");
const newUserPw = ref("");
const newUserPwCheck = ref("");
const form = ref();

/*************** functions ***************/

const onEditSubmit = async () => {
  // const { valid } = await form.value.validate();

  // if (!valid) throw new Error("입력이 양식에 맞지 않습니다.");

  await edit({
    userId: user.value.userId,
    newUserName: newUserName.value,
    oldUserPw: oldUserPw.value,
    newUserPw: newUserPw.value,
    newUserPwCheck: newUserPwCheck.value,
  });

  // 유저 상태 저장
  user.value = {
    ...user.value,
    userName: newUserName.value,
  };

  message.value = "정보가 수정되었습니다.";
  toast.value = true;

  router.push("/");
};
</script>
<template>
  <div
    class="m-auto w-screen max-w-md overflow-hidden rounded-3xl bg-white text-sm leading-6 shadow-lg ring-1 ring-gray-900/5"
  >
    <div class="flex flex-1 flex-col justify-center px-6 py-12 lg:px-8">
      <div class="sm:mx-auto sm:w-full sm:max-w-sm">
        <h1 class="mt-10 text-center text-3xl font-bold leading-9 tracking-tight text-gray-900">
          프로필을 수정하세요.
        </h1>
      </div>

      <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form class="space-y-6" @submit.prevent="onEditSubmit" ref="form">
          <div>
            <label for="email" class="block text-sm font-medium leading-6 text-gray-900"
              >새 닉네임</label
            >
            <div class="mt-2">
              <input
                v-model="newUserName"
                :placeholder="user.userName"
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
              >현재 비밀번호</label
            >
            <div class="mt-2">
              <input
                v-model="oldUserPw"
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
                >새 비밀번호</label
              >
            </div>
            <div class="mt-2">
              <input
                v-model="newUserPw"
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
                >새 비밀번호 확인</label
              >
            </div>
            <div class="mt-2">
              <input
                v-model="newUserPwCheck"
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
              수정
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<!-- 
<template>
  <v-layout class="align-center mx-auto">
    <v-sheet width="600">
      <v-form fast-fail @submit.prevent="onEditSubmit" validate-on="input submit lazy" ref="form">
        <v-text-field
          v-model="newUserName"
          label="새 닉네임"
          placeholder="새 닉네임"
          required
          :rules="userNameRule"
          type="text"
        ></v-text-field>
        <v-text-field
          v-model="oldUserPw"
          label="현재 비밀번호"
          placeholder="현재 비밀번호"
          required
          :rules="userPwRule"
          type="password"
        ></v-text-field>
        <v-text-field
          label="새 비밀번호"
          v-model="newUserPw"
          placeholder="새 비밀번호"
          required
          :rules="userPwRule"
          type="password"
        ></v-text-field>
        <v-text-field
          v-model="newUserPwCheck"
          label="새 비밀번호 확인"
          placeholder="새 비밀번호 확인"
          required
          :rules="[
            ...userPwCheckRule,
            (newUserPwCheck) => newUserPwCheck === newUserPw || `비밀번호가 일치하지 않습니다.`,
          ]"
          type="password"
        ></v-text-field>
        <v-btn type="submit" block class="mt-2">수정</v-btn>
      </v-form>
      <v-btn block class="mt-5 bg-red" @click="onRemoveClick">탈퇴</v-btn>
    </v-sheet>
  </v-layout>
</template> -->

<style scoped></style>
