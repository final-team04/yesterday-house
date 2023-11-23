<script setup>
// import HouseCommentItemComponent from "./HouseCommentItemComponent.vue";
import CommentItemComponent from "../comment/CommentItemComponent.vue";
import { ref, onMounted, watch } from "vue";
import { _getCommentList, _createComment } from "../../api/Comment";
import { storeToRefs } from "pinia";
import { useUserStore } from "../../stores/counter";

const user = storeToRefs(useUserStore()).user;

const commentList = ref();
const content = ref();

const props = defineProps({
  board: Object,
  isSelect: Boolean,
});

watch(
  () => props.isSelect,
  () => {
    getCommentList();
  },
  { deep: true },
);

onMounted(() => {
  getCommentList();
});

const getCommentList = async () => {
  const response = await _getCommentList(props.board.bno);
  commentList.value = response;
};

const emits = defineEmits(["closeDetail"]);

const closeDetail = () => {
  emits("closeDetail");
};

const createComment = async () => {
  let data = {
    bno: props.board.bno,
    userId: user.value.userId,
    content: content.value,
  };

  await _createComment(data);

  content.value = "";

  getCommentList();
};
</script>

<template>
  <v-navigation-drawer width="300" location="right">
    <v-btn @click="closeDetail"> X </v-btn>
    <v-list-item :title="props.board.bno + '. ' + props.board.title"></v-list-item>
    <p>{{ props.board.content }}</p>
    <br />
    <p>작성자 : {{ props.board.userName }}[{{ props.board.userId }}]</p>
    <p>작성일자 : {{ props.board.date }}</p>
    <v-divider />
    <v-list-item>댓글</v-list-item>
    <v-divider />
    <CommentItemComponent
      v-for="(comment, index) in commentList"
      :comment="comment"
      :key="index"
      @get-comment-list="getCommentList"
    />
    <v-text-field label="댓글" variant="outlined" v-model="content"></v-text-field>
    <v-btn @click="createComment"> 댓글쓰기 </v-btn>
  </v-navigation-drawer>
</template>

<style scoped></style>
