<script setup>
import { storeToRefs } from "pinia";
import { useUserStore } from "../../stores/counter";

const user = storeToRefs(useUserStore()).user;

const props = defineProps({
  board: Object,
});

console.log(props.board.userId);
console.log(user.userId);
const emits = defineEmits(["getBoardData", "modifyBoard", "deleteBoard"]);

const getBoardData = () => {
  emits("getBoardData", props.board);
};

const modifyBoard = () => {
  emits("modifyBoard", props.board);
};

const deleteBoard = () => {
  emits("deleteBoard", props.board.bno);
};
</script>

<template>
  <div
    @click="getBoardData"
    class="min-w-0 flex-auto rounded-lg hover:bg-gray-100 hover:cursor-pointer"
  >
    <div class="flex justify-between items-center mt-5 mb-2">
      <h3 class="text-base font-semibold leading-7 text-gray-900">
        {{ props.board.title }}
      </h3>

      <div v-if="props.board.userId === user.userId" class="flex justify-center">
        <button
          id="modifyBoardButton"
          class="flex items-center h-6 w-6 rounded-md bg-indigo-500 px-0 mx-1 py-0 text-sm font-semibold leading-6 text-white hover:bg-indigo-600 shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          @click.stop="modifyBoard"
        >
          <svg
            class="h-5 w-5 text-white flex-auto"
            viewBox="0 0 20 20"
            fill="currentColor"
            aria-hidden="true"
          >
            <path
              d="M2.695 14.763l-1.262 3.154a.5.5 0 00.65.65l3.155-1.262a4 4 0 001.343-.885L17.5 5.5a2.121 2.121 0 00-3-3L3.58 13.42a4 4 0 00-.885 1.343z"
            ></path>
          </svg>
        </button>
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
    </div>
    <div class="mt-5 mb-2">
      <p class="mt-1 truncate text-xs leading-5 text-gray-500">
        {{ `${props.board.userName}님이 작성한 글` }}
      </p>
      <p class="truncate text-xs leading-5 text-gray-500">
        {{ props.board.date }}
      </p>
    </div>
  </div>
</template>

<style scoped>
.board:hover {
  background-color: rgb(243 244 246);
  cursor: pointer;
}
</style>
