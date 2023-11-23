<script setup>
import { storeToRefs } from "pinia";
import { useMessage } from "../stores/counter";
const { message, toast } = storeToRefs(useMessage());
</script>

<template>
  <div id="toast" class="text-center">
    <v-snackbar
      v-model="toast"
      timeout="1500"
      :color="
        message === '로그아웃 되었습니다.'
          ? 'rgb(163 163 163)'
          : message === '회원 탈퇴가 완료되었습니다.'
          ? 'red'
          : '#6366f1'
      "
    >
      {{ message }}
    </v-snackbar>
  </div>
</template>

<style scoped>
#toast {
  z-index: 999999;
}
</style>
