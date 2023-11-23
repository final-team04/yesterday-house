<script setup>
import { storeToRefs } from "pinia";
import { useAptInfo } from "../../stores/counter";

const aptInfo = useAptInfo();

const emits = defineEmits(["pickApt"]);
const props = defineProps({
  data: Object,
});

const formatDealAmount = (amount) => {
  let number = Number(amount.replace(/,/g, ""));
  let result = "";
  if (number > 10000) {
    result += number / 10000 + "억 ";
  }
  if (number % 10000 != 0) {
    result += (number % 10000) + "만원";
  }
  return result;
};

const pickApt = () => {
  aptInfo.pickAptData = props.data;
  emits("pickApt", props.data);
};
</script>

<template>
  <li
    class="flex justify-between px-1 rounded-lg gap-x-6 py-5 hover:bg-gray-100 hover:cursor-pointer"
    v-if="props.data"
    @click="pickApt"
  >
    <div class="flex min-w-0 gap-x-4">
      <div class="min-w-0 flex-auto">
        <p class="text-sm font-semibold leading-6 text-gray-900">{{ props.data.apartmentName }}</p>
        <p class="mt-1 truncate text-xs leading-5 text-gray-500">
          {{ formatDealAmount(props.data.dealAmount) }}
        </p>
      </div>
    </div>
    <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
      <p class="text-sm leading-6 text-gray-900">
        {{ props.data.dealYear }}년 {{ props.data.dealMonth }}월 {{ props.data.dealDay }}일
      </p>
    </div>
  </li>

  <!-- <v-list-item v-if="props.data" @click="pickApt">
    <h4>{{ props.data.apartmentName }}</h4>
    <p>{{ formatDealAmount(props.data.dealAmount) }}</p>
    <p>{{ props.data.dealYear }}년 {{ props.data.dealMonth }}월 {{ props.data.dealDay }}일</p>
  </v-list-item> -->
  <!-- data가 로드되기 전에 렌더링되는 경우 처리 -->
  <!-- <v-list-item v-else>
    <p>Loading...</p>
  </v-list-item> -->
</template>

<style scoped></style>
