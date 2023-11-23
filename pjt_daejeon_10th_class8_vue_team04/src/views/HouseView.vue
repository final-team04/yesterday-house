<script setup>
import { ref } from "vue";
import HouseListComponent from "../components/house/HouseListComponent.vue";
import HouseMapComponent from "../components/map/HouseMapComponent.vue";
import HouseDetailComponent from "../components/house/HouseDetailComponent.vue";
// import HouseBoardDetailComponent from "../components/house/HouseBoardDetailComponent.vue";
// import HouseBoardCreateComponent from "../components/house/HouseBoardCreateComponent.vue";
// import HouseBoardModifyComponent from "../components/house/HouseBoardModifyComponent.vue";

import BoardDetailComponent from "../components/board/BoardDetailComponent.vue";
import BoardCreateComponent from "../components/board/BoardCreateComponent.vue";
import BoardModifyComponent from "../components/board/BoardModifyComponent.vue";

const aptData = ref();

const boardData = ref();
const aptCode = ref();
const createBoardCheck = ref(false);
const isSelect = ref(false);

const houseDetailIsOpen = ref(false);
const boardDetailIsOpen = ref(false);
const boardCreateIsOpen = ref(false);
const boardModifyIsOpen = ref(false);

function pickApt(data) {
  aptData.value = data;
  houseDetailIsOpen.value = true;
  boardDetailIsOpen.value = false;
  boardCreateIsOpen.value = false;
  boardModifyIsOpen.value = false;
}

function getBoardData(board) {
  boardData.value = board;
  isSelect.value = !isSelect.value;
  if (boardCreateIsOpen.value || boardModifyIsOpen.value) {
    boardCreateIsOpen.value = false;
    boardModifyIsOpen.value = false;
    setTimeout(() => {
      boardDetailIsOpen.value = true;
    }, 400);
  } else {
    boardDetailIsOpen.value = true;
  }
}

function closeAll() {
  houseDetailIsOpen.value = false;
  boardDetailIsOpen.value = false;
  boardCreateIsOpen.value = false;
  boardModifyIsOpen.value = false;
}

function closeDetail() {
  boardDetailIsOpen.value = false;
}

function createBoard(data) {
  aptCode.value = data;

  if (boardDetailIsOpen.value || boardModifyIsOpen.value) {
    boardDetailIsOpen.value = false;
    boardModifyIsOpen.value = false;
    setTimeout(() => {
      boardCreateIsOpen.value = true;
    }, 400);
  } else {
    boardCreateIsOpen.value = true;
  }
}

function closeBoardDetail() {
  boardCreateIsOpen.value = false;
}

function createBoardSuccess() {
  createBoardCheck.value = !createBoardCheck.value;
  boardCreateIsOpen.value = false;
}

function modifyBoard(data) {
  boardData.value = data;
  if (boardDetailIsOpen.value || boardCreateIsOpen.value) {
    boardDetailIsOpen.value = false;
    boardCreateIsOpen.value = false;
    setTimeout(() => {
      boardModifyIsOpen.value = true;
    }, 400);
  } else {
    boardModifyIsOpen.value = true;
  }
}

function closeBoardModify() {
  boardModifyIsOpen.value = false;
}

function modifyBoardSuccess() {
  createBoardCheck.value = !createBoardCheck.value;
  boardModifyIsOpen.value = false;
}
</script>

<template>
  <v-main class="d-flex justify-space-between">
    <HouseListComponent @pick-apt="pickApt" />

    <HouseMapComponent class="flex-grow-1" :data="aptData" @pick-apt="pickApt" />

    <!-- <template v-if="boardCreateIsOpen"> -->
    <BoardCreateComponent
      v-if="aptData"
      :data="aptData.aptCode"
      @close-board-detail="closeBoardDetail"
      @create-board-success="createBoardSuccess"
    />
    <!-- </template> -->

    <!-- <template v-if="boardModifyIsOpen"> -->
    <BoardModifyComponent
      v-if="boardData"
      :board="boardData"
      @close-board-modify="closeBoardModify"
      @modify-board-success="modifyBoardSuccess"
    />
    <!-- </template> -->

    <BoardDetailComponent
      v-if="boardDetailIsOpen"
      :board="boardData"
      :isSelect="isSelect"
      @close-detail="closeDetail"
    />

    <HouseDetailComponent
      v-if="houseDetailIsOpen"
      :data="aptData"
      :createBoardCheck="createBoardCheck"
      @get-board-data="getBoardData"
      @close-all="closeAll"
      @create-board="createBoard"
      @modify-board="modifyBoard"
    />
  </v-main>
</template>

<style scoped>
.v-main {
  height: calc(100vh - 4rem);
}
</style>
