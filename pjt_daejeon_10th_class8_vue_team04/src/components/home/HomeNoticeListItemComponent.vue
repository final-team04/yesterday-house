<script setup>
const props = defineProps({
  notice: Object,
});

const { title, content, hit, date } = props.notice;

/**
  private int bno;
  private String title;
  private String content;
  private int hit;
  private String date;
 */
</script>
<template>
  <div class="group relative flex gap-x-6 rounded-lg p-4 hover:bg-gray-50">
    <div
      class="mt-1 flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-white"
    >
      <svg
        class="h-6 w-6 text-gray-600 group-hover:text-indigo-600"
        fill="none"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="currentColor"
        aria-hidden="true"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          d="M15.042 21.672L13.684 16.6m0 0l-2.51 2.225.569-9.47 5.227 7.917-3.286-.672zM12 2.25V4.5m5.834.166l-1.591 1.591M20.25 10.5H18M7.757 14.743l-1.59 1.59M6 10.5H3.75m4.007-4.243l-1.59-1.59"
        />
      </svg>
    </div>
    <div>
      <a class="font-semibold text-gray-900">
        {{ title }}
        <span class="absolute inset-0"></span>
      </a>
      <p class="mt-1 text-gray-600">{{ content }}</p>
    </div>
  </div>
</template>

<style scoped></style>
