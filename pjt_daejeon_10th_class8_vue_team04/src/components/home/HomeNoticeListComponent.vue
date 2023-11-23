<script setup>
import HomeNoticeListItemComponent from "./HomeNoticeListItemComponent.vue";
import { _getNoticeList } from "../../api/Board";
import { ref, onMounted } from "vue";

const noticeList = ref(null);

onMounted(async () => {
  const data = await _getNoticeList();
  console.log(data);
  if (data.errorMessage) {
    throw new Error("공지사항을 불러오지 못했습니다.");
  }
  noticeList.value = data || null;
});
</script>

<template>
  <div class="left-1/2 z-10 mt-5 w-screen max-w-max px-4">
    <!-- noticelist -->
    <div
      class="noticeList w-screen max-w-md flex-auto overflow-hidden rounded-3xl bg-white text-sm leading-6 shadow-lg ring-1 ring-gray-900/5"
    >
      <div v-if="noticeList" class="p-4">
        <HomeNoticeListItemComponent
          v-for="notice in noticeList"
          :key="notice.bno"
          :notice="notice"
        >
        </HomeNoticeListItemComponent>
      </div>
      <div v-else class="p-4">
        <div class="group relative flex gap-x-6 rounded-lg p-4 hover:bg-gray-50">
          <div
            class="mt-1 flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-white"
          ></div>
          <div>
            <a class="font-semibold text-gray-900">
              공지사항이 없습니다.
              <span class="absolute inset-0"></span>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.noticeList {
  height: 60vh;
}
</style>
