<script setup>
const props = defineProps({
  news: Object,
});

const { title, originallink, link, description, pubDate } = props.news;

const date = new Date(pubDate);
// date.toDateString();
const onClick = () => {
  window.open(originallink);
};
</script>

<template>
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
      <a href class="font-semibold text-gray-900" @click="onClick" v-html="title"> </a>
      <!-- <p class="mt-1 text-gray-600">{{ description }}</p> -->
    </div>
  </div>
</template>

<style scoped></style>
