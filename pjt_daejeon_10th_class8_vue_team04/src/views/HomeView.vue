<script setup>
import HomeFormComponent from "../components/home/HomeFormComponent.vue";
import HomeInfoComponent from "../components/home/HomeInfoComponent.vue";
import FooterComponent from "../components/FooterComponent.vue";
// import { useRouter } from "vue-router";
</script>

<template>
  <v-main class="d-flex flex-column align-center justify-center">
    <HomeFormComponent />
    <HomeInfoComponent />
  </v-main>
  <!-- <FooterComponent /> -->
</template>

<style scoped></style>
