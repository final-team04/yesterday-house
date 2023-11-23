<script setup>
import RadarChartComponent from "./RadarChartComponent.vue";
import BarChartComponent from "./BarChartComponent.vue";
import { ref, toRefs, watch } from "vue";
import { useAptInfo } from "../../stores/counter";
import { storeToRefs } from "pinia";

const props = defineProps({
  data: Object,
});

const { data } = toRefs(props);
const dataAvg = ref();
const dataListAvg = ref();
const inCircleAvg = ref();

const { inCircleInfo, inCircleList } = storeToRefs(useAptInfo());

const makeAvg = () => {
  let avg = 0;
  for (const key in inCircleInfo.value) {
    switch (key) {
      case "MT1":
        avg += inCircleInfo.value[key] * 10;
        break;
      case "CS2":
        avg += inCircleInfo.value[key] * 5;
        break;
      case "PS3":
        avg += inCircleInfo.value[key] * 20;
        break;
      case "SC4":
        avg += inCircleInfo.value[key] * 20;
        break;
      case "SW8":
        if (inCircleInfo.value[key] >= 1) {
          avg += 45 * 20;
        }
        break;
      case "HP8":
        avg += inCircleInfo.value[key] * 20;
        break;
      case "PM9":
        avg += inCircleInfo.value[key] * 10;
        break;
      case "CT1":
        avg += inCircleInfo.value[key] * 5;
        break;
      case "BK9":
        avg += inCircleInfo.value[key] * 5;
        break;
      case "PO3":
        avg += inCircleInfo.value[key] * 20;
        break;
    }
  }

  inCircleAvg.value = Math.floor(avg / 45);

  let dealAmountAvg = 0,
    areaAvg = 0,
    floorAvg = 0,
    buildYearAvg = 0;

  for (const key in inCircleList.value) {
    dealAmountAvg += inCircleList.value[key].dealAmount.includes(",")
      ? parseInt(inCircleList.value[key].dealAmount.replace(/,/g, ""), 10)
      : parseInt(inCircleList.value[key].dealAmount, 10);
    areaAvg += parseInt(inCircleList.value[key].area);
    floorAvg += parseInt(inCircleList.value[key].floor);
    buildYearAvg += parseInt(inCircleList.value[key].buildYear);
  }

  let length = inCircleList.value.length;

  dataListAvg.value = {
    dealAmountAvg: Math.min(((dealAmountAvg / length - 100) / (100000 - 100)) * 100, 100),
    areaAvg: Math.min(((areaAvg / length - 9) / (200 - 9)) * 100),
    floorAvg: Math.min(((floorAvg / length - 1) / (30 - 1)) * 100),
    buildYearAvg: Math.min(buildYearAvg / length - (new Date(Date.now()).getFullYear() - 100), 100),
  };

  dataAvg.value = {
    dealAmountAvg: Math.min(
      ((data.value.dealAmount.includes(",")
        ? parseInt(data.value.dealAmount.replace(/,/g, ""), 10)
        : parseInt(data.value.dealAmount, 10) - 100) /
        (100000 - 100)) *
        100,
      100
    ),
    areaAvg: Math.min(((parseFloat(data.value.area) - 9) / (200 - 9)) * 100, 100),
    floorAvg: Math.min(((parseInt(data.value.floor) - 1) / (30 - 1)) * 100, 100),
    buildYearAvg: Math.min(data.value.buildYear - (new Date(Date.now()).getFullYear() - 100), 100),
  };
};

makeAvg();

watch(
  () => data.value,
  () => {
    makeAvg();
  },
  { deep: true }
);

const modal = ref(false);
</script>

<template>
  <!-- <RadarChartComponent :data="data" @click="modal = true" /> -->

  <div @click="modal = true">
    <RadarChartComponent
      v-if="data"
      :data="dataAvg"
      :data-name="data.apartmentName"
      :data-avg="dataListAvg"
      :data-avg-name="'주변 매물 평균'"
    />
    <BarChartComponent :data="inCircleInfo" />
  </div>
  <v-dialog v-model="modal" width="550">
    <v-card @click="modal = false" class="d-flex justify-center">
      <RadarChartComponent
        v-if="data"
        :data="dataAvg"
        :data-name="data.apartmentName"
        :data-avg="dataListAvg"
        :data-avg-name="'1km 반경 종합(평균)'"
      />

      <BarChartComponent :data="inCircleInfo" />
      <h3 class="text-xl text-center mb-2 font-semibold leading-7 text-gray-900">
        종합 점수 : {{ inCircleAvg }} / 100
      </h3>
    </v-card>
  </v-dialog>

  <!-- <RadarChartComponent :data="data" width="auto" />-->
</template>

<style scoped></style>
