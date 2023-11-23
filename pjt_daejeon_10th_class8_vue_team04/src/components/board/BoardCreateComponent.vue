<script setup>
import { ref } from "vue";
import { _createBoard } from "../../api/Board";
import { storeToRefs } from "pinia";
import { useUserStore } from "../../stores/counter";
const title = ref();
const content = ref();

const { user } = storeToRefs(useUserStore());

const props = defineProps({
  data: Object,
});

const create = ref(false);
const emits = defineEmits(["closeBoardDetail", "createBoardSuccess"]);

const createBoard = async () => {
  let data = {
    aptCode: props.data,
    userId: user.value.userId,
    title: title.value,
    content: content.value,
  };
  console.log(data);
  await _createBoard(data);

  create.value = false;
  emits("createBoardSuccess");
};
</script>

<!-- class="mt-10 sm:mx-auto sm:w-full bg-white sm:max-w-sm" -->
<template>
  <v-overlay
    v-model="create"
    activator="#createBoardButton"
    class="flex justify-center items-center"
  >
    <div class="z-40 w-80 h-full rounded-3xl bg-white p-5 divide-y divide-gray-100 overflow-auto">
      <form class="space-y-6" @submit.prevent="createBoard" ref="form">
        <div>
          <label for="email" class="block text-sm font-semibold leading-6 text-gray-900"
            >제목</label
          >
          <div class="mt-2">
            <input
              v-model="title"
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
            <label for="password" class="block text-sm font-semibold leading-6 text-gray-900"
              >내용을 작성하세요.
            </label>
          </div>
          <div class="mt-2">
            <textarea
              v-model="content"
              rows="5"
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
            작성하기
          </button>
        </div>
      </form>
    </div>
  </v-overlay>
  <!-- <div
    role="list"
    class="bg-tranparent z-40 w-80 h-full rounded-l-3xl bg-white p-4 divide-y divide-gray-100 overflow-auto"
  >
    <div class="flex justify-between px-1 rounded-lg gap-x-6 py-5">
      <div class="flex min-w-0 gap-x-4">
        <div class="min-w-0 flex-auto">
          <h3 class="text-xl mb-2 font-semibold leading-7 text-gray-900">제목</h3>
          <p class="mt-1 truncate text-xs leading-5 text-gray-500">내용</p>
        </div>
      </div>

      <button
        class="h-6 w-6 rounded-md bg-red-400 px-0 mx-1 py-0 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-red-600 flex"
        @click.stop="deleteBoard"
      >
        <svg
          class="h-6 w-6"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          aria-hidden="true"
        >
          <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"></path>
        </svg>
      </button>
    </div>
    <button
      type="submit"
      class="w-full mt-2 rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
      @click="createBoard"
    >
      작성하기
    </button>
  </div> -->

  <!-- 
  <v-navigation-drawer width="300" location="end">
    게시물 생성
    <v-divider></v-divider>
    <p>{{ props.aptCode }}</p>
    <v-text-field label="제목" variant="outlined" v-model="title"></v-text-field>
    <v-textarea label="내용" variant="outlined" v-model="content"></v-textarea>
    <v-btn @click="createBoard"> 글쓰기 </v-btn>
    <v-btn @click="closeBoardDetail"> 취소 </v-btn>
  </v-navigation-drawer>
   -->
</template>

<style scoped></style>
