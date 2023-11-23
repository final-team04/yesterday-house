<script setup>
import { onMounted, ref } from "vue";
import { getNews } from "../../api/Home";

import HomeNewsListItemComponent from "./HomeNewsListItemComponent.vue";

const newsList = ref(null);
onMounted(async () => {
  const data = await getNews();
  if (data.errorMessage) throw new Error("뉴스를 불러오지 못했습니다.");
  newsList.value = data.items.slice(0, 5);
});
</script>

<template>
  <!-- newslist  -->
  <div class="left-1/2 z-10 mt-5 w-screen max-w-max px-4">
    <div
      class="w-screen max-w-md flex-auto overflow-hidden rounded-3xl bg-white text-sm leading-6 shadow-lg ring-1 ring-gray-900/5"
    >
      <div
        class="newsList w-screen max-w-md flex-auto overflow-hidden rounded-3xl bg-white text-sm leading-6 shadow-lg ring-1 ring-gray-900/5"
      >
        <div class="p-4" v-if="newsList">
          <HomeNewsListItemComponent
            v-for="news in newsList"
            :key="news.originallink"
            :news="news"
          />
        </div>
        <div class="p-4" v-else>
          <div class="group relative flex gap-x-6 rounded-lg p-4 hover:bg-gray-50">
            <div
              class="mt-1 flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-white"
            >
              <svg
                class="h-5 w-5 flex-none stroke-slate-600 text-gray-600 group-hover:text-indigo-600"
                fill="none"
                stroke-width="1.5"
                stroke-linecap="round"
                stroke-linejoin="round"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M17.25 10c0 1-1.75 6.25-7.25 6.25S2.75 11 2.75 10 4.5 3.75 10 3.75 17.25 9 17.25 10Z"
                ></path>
                <circle cx="10" cy="10" r="2.25"></circle>
              </svg>
            </div>
            <div>
              <div class="font-semibold text-gray-900">뉴스를 불러오지 못했습니다.</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.newsList {
  height: 60vh;
}
</style>
