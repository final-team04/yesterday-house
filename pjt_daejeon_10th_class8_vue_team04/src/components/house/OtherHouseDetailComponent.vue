<script setup>
import { ref } from "vue";
import RadarChartComponent from "../chart/RadarChartComponent.vue";

const props = defineProps({
  data: Object,
  apt: Object,
  apartmentName: String,
});

const isOpen = ref(false);

const open = () => {
  isOpen.value = !isOpen.value;
};

const dealDate = (item) => ` ${item.dealYear}년 ${item.dealMonth}월 ${item.dealDay}일`;

const formatDealAmount = (amount) => {
  let number = Number(amount.replace(/,/g, ""));
  let result = "";
  if (number > 10000) {
    result += Math.floor(number / 10000) + "억 ";
  }
  if (number % 10000 != 0) {
    result += (number % 10000) + "만원";
  }
  return result;
};

const aptAvg = ref({
  dealAmountAvg: Math.min(
    ((props.data.dealAmount.includes(",")
      ? parseInt(props.data.dealAmount.replace(/,/g, ""), 10)
      : parseInt(props.data.dealAmount, 10) - 100) /
      (100000 - 100)) *
      100,
    100
  ),
  areaAvg: Math.min(((parseFloat(props.data.area) - 9) / (200 - 9)) * 100, 100),
  floorAvg: Math.min(((parseInt(props.data.floor) - 1) / (30 - 1)) * 100, 100),
  buildYearAvg: Math.min(props.data.buildYear - (new Date(Date.now()).getFullYear() - 100), 100),
});

let dealAmountAvg = 0,
  areaAvg = 0,
  floorAvg = 0,
  buildYearAvg = 0;

for (const key in props.apt) {
  dealAmountAvg += props.apt[key].dealAmount.includes(",")
    ? parseInt(props.apt[key].dealAmount.replace(/,/g, ""), 10)
    : parseInt(props.apt[key].dealAmount, 10);
  areaAvg += parseInt(props.apt[key].area);
  floorAvg += parseInt(props.apt[key].floor);
  buildYearAvg += parseInt(props.apt[key].buildYear);
}

let length = props.apt.length;

const aptOtherAvg = ref({
  dealAmountAvg: Math.min(((dealAmountAvg / length - 100) / (100000 - 100)) * 100, 100),
  areaAvg: Math.min(((areaAvg / length - 9) / (200 - 9)) * 100),
  floorAvg: Math.min(((floorAvg / length - 1) / (30 - 1)) * 100),
  buildYearAvg: Math.min(buildYearAvg / length - (new Date(Date.now()).getFullYear() - 100), 100),
});

const area = (area) => {
  return (parseFloat(area) / 3.3058).toFixed(2) + " 평";
};
</script>

<template>
  <div @click="open">
    <div
      class="flex justify-between px-1 h-10 rounded-lg min-w-0 gap-x-6 py-2 hover:bg-gray-100 hover:cursor-pointer"
    >
      <h3 class="text-base mb-2 font-semibold leading-7 text-gray-900">
        {{ props.apartmentName }}
      </h3>
      <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
        <p class="text-sm leading-6 text-gray-900">{{ `${props.apt.length}건` }}</p>
      </div>
    </div>
    <div v-show="isOpen" class="px-1 rounded-lg min-w-0 gap-x-6 py-2">
      <RadarChartComponent
        v-show="data"
        :data="aptAvg"
        :data-name="props.data.apartmentName"
        :data-avg="aptOtherAvg"
        :data-avg-name="props.apt[0].apartmentName + '(평균)'"
        width="auto"
      />
      <h3
        class="text-sm mt-3 pt-2 mb-2 font-semibold leading-7 text-gray-900 border-t border-gray-100"
      >
        거래 내역
      </h3>
      <p
        class="text-gray-400 text-xs mb-1 font-semibold leading-6"
        v-for="(item, index) in props.apt"
        :key="index"
      >
        {{ `${dealDate(item)}, ${formatDealAmount(item.dealAmount)}에 거래되었어요.` }}
        <br>
        {{ item.floor }}층 : {{ item.area }}m<sup>2</sup> ({{ area(item.area) }})
        <hr />
      </p>
    </div>
  </div>

  <!-- <v-list-item :title="props.apartmentName + ' : ' + props.apt.length + '건'" @click="open">
    <template v-if="isOpen">
      <RadarChartComponent
        v-if="data"
        :data="aptAvg"
        :data-name="props.data.apartmentName"
        :data-avg="aptOtherAvg"
        :data-avg-name="props.apt[0].apartmentName + '(평균)'"
        width="auto"
      />
      <p v-for="(item, index) in props.apt" :key="index">
        {{ dealDate(item) }} : {{ formatDealAmount(item.dealAmount) }} <br />
        {{ item.floor }}층 : {{ item.area }}m<sup>2</sup> ({{ area(item.area) }})<br /><br />
      </p>
    </template>
  </v-list-item> -->
</template>

<style scoped></style>
