<script setup>
import { ref } from "vue";
import { _deleteComment, _modifyComment } from "../../api/Comment";

const props = defineProps({
  comment: Object,
});

const emits = defineEmits(["getCommentList"]);

const modifyCheck = ref(false);
const content = ref("");

content.value = props.comment.content;

// 여기에서 삭제하기

const deleteComment = async () => {
  console.log("삭제");
  await _deleteComment(props.comment.cno);

  emits("getCommentList");
};

const openModify = () => {
  modifyCheck.value = true;
};

const closelModify = () => {
  modifyCheck.value = false;
};

const modifyComment = async () => {
  modifyCheck.value = false;
  let data = {
    cno: props.comment.cno,
    content: content.value,
  };
  await _modifyComment(data);
  emits("getCommentList");
};
</script>

<template>
  <v-list-item v-if="!modifyCheck">
    <p>{{ props.comment.uid }}</p>
    <p>{{ props.comment.content }}</p>
    <p>{{ props.comment.date }}</p>
  </v-list-item>
  <v-list-item v-else>
    <p>{{ props.comment.uid }}</p>
    <v-text-field label="댓글" variant="outlined" v-model="content"></v-text-field>
    <v-list-item>
      <v-btn @click="modifyComment">수정</v-btn>
    </v-list-item>
    <v-list-item>
      <v-btn @click="closelModify">취소</v-btn>
    </v-list-item>
  </v-list-item>
  <v-list-item v-if="!modifyCheck">
    <v-btn @click="openModify">수정</v-btn>
  </v-list-item>
  <v-list-item v-if="!modifyCheck">
    <v-btn @click="deleteComment">삭제</v-btn>
  </v-list-item>
</template>

<style scoped></style>
