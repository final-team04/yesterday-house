<script setup>
import { storeToRefs } from "pinia";
import { useError } from "../stores/counter";
// import { watch, ref, toRefs } from "vue";

// global states
const { error, modal } = storeToRefs(useError());
</script>

<template>
  <v-dialog v-model="modal" width="500" id="error" class="text-center">
    <v-card class="flex items-center" height="250">
      <!-- <v-card-text class="text-red"> -->

      <div class="mt-12 mb-8 flex items-center justify-center h-12 w-12 rounded-full bg-red-100">
        <svg
          class="h-6 w-6 text-red-600"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
          aria-hidden="true"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M6 18L18 6M6 6l12 12"
          />
        </svg>
      </div>
      <div class="text-center sm:mt-5">
        <h3 class="text-lg leading-6 font-medium text-gray-900" id="modal-headline">
          {{ error }}
        </h3>
        <div class="mt-2">
          <p class="text-sm text-gray-500">잠시 후에 다시 시도해 주세요.</p>
        </div>
      </div>

      <!-- {{ error }} -->
    </v-card>
  </v-dialog>
</template>

<style scoped>
#error {
  z-index: 999999;
}
</style>
