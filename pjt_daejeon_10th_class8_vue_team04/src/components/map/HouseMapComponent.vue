<script setup>
import KakaoMap from "./KakaoMap.vue";

const props = defineProps({
  data: Object,
});

const emits = defineEmits(["pickApt"]);

const pickApt = (data) => {
  emits("pickApt", data);
};
</script>

<template>
  <!-- <v-main class="d-flex align-center justify-center" style="min-height: 100vh"> -->
  <KakaoMap :latitude="37.5665" :longitude="126.978" :data="props.data" @pick-apt="pickApt" />
</template>

<style scoped></style>
