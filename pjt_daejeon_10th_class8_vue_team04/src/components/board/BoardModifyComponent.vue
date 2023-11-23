<script setup>
import { ref, watch } from "vue";
import { _modifyBoard } from "../../api/Board";

const props = defineProps({
  board: Object,
});
console.log(props);

const emits = defineEmits(["closeBoardModify", "modifyBoardSuccess"]);

const modify = ref(false);
const title = ref();
const content = ref();

const closeBoardModify = () => {
  emits("closeBoardModify");
};

const modifyBoard = async () => {
  let data = {
    bno: props.board.bno,
    uid: props.board.uid,
    content: content.value,
  };
  await _modifyBoard(data);

  emits("modifyBoardSuccess");
};

content.value = props.board.content;

watch(
  () => props.board,
  () => {
    console.log(props.board);
    content.value = props.board.content;
  },
  { deep: true }
);
</script>

<template>
  <v-overlay
    v-model="modify"
    activator="#modifyBoardButton"
    class="flex justify-center items-center"
  >
    <div class="z-40 w-80 h-full rounded-3xl bg-white p-5 divide-y divide-gray-100 overflow-auto">
      <form class="space-y-6" @submit.prevent="modifyBoard" ref="form">
        <div>
          <label for="email" class="block text-sm font-semibold leading-6 text-gray-900"
            >제목</label
          >
          <div class="mt-2">
            <input
              v-model="title"
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

  <!-- 
  <v-navigation-drawer width="300" location="end">
    게시물 수정
    <v-divider></v-divider>
    <v-list-item :title="props.board.bno + '. ' + props.board.title"></v-list-item>
    <v-textarea label="내용" variant="outlined" v-model="content"></v-textarea>
    <v-btn @click="ModifyBoard"> 수정하기 </v-btn>
    <v-btn @click="closeBoardModify"> 취소 </v-btn>
  </v-navigation-drawer>
   -->
</template>

<style scoped></style>
