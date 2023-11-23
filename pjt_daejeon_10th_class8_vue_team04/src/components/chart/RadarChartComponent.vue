<script setup>
import { ref, watch, toRefs } from "vue";
import { Radar } from "vue-chartjs";
import { Chart, registerables } from "chart.js/auto";

const props = defineProps({
  data: Object,
  dataName: String,
  dataAvg: Object,
  dataAvgName: String,
});

const { data, dataAvg } = toRefs(props);
const radar = ref();

watch(
  dataAvg,
  () => {
    const newDataAvgArray = [
      dataAvg.value.dealAmountAvg,
      dataAvg.value.areaAvg,
      dataAvg.value.floorAvg,
      dataAvg.value.buildYearAvg,
    ];
    const newDataArray = [
      data.value.dealAmountAvg,
      data.value.areaAvg,
      data.value.floorAvg,
      data.value.buildYearAvg,
    ];
    // 지역 평균
    radar.value.chart.data.datasets[0].data = newDataAvgArray;
    // 선택한 매물
    radar.value.chart.data.datasets[1].data = newDataArray;

    radar.value.chart.update();
  },
  { deep: true },
);

const chartData = {
  labels: ["거래 금액", "면적", "층수", "건축년도"],
  datasets: [
    {
      label: props.dataAvgName,
      data: [
        dataAvg.value.dealAmountAvg,
        dataAvg.value.areaAvg,
        dataAvg.value.floorAvg,
        dataAvg.value.buildYearAvg,
      ],

      fill: true,
      backgroundColor: "rgba(255, 99, 132, 0.2)",
      borderColor: "rgb(255, 99, 132)",
      pointBackgroundColor: "rgb(255, 99, 132)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgb(255, 99, 132)",
    },
    {
      label: props.dataName,
      data: [
        data.value.dealAmountAvg,
        data.value.areaAvg,
        data.value.floorAvg,
        data.value.buildYearAvg,
      ],
      fill: true,
      backgroundColor: "rgba(54, 162, 235, 0.2)",
      borderColor: "rgb(54, 162, 235)",
      pointBackgroundColor: "rgb(54, 162, 235)",
      pointBorderColor: "#fff",
      pointHoverBackgroundColor: "#fff",
      pointHoverBorderColor: "rgb(54, 162, 235)",
    },
  ],
};

const chartOptions = ref({
  responsive: true,
  scales: {
    r: {
      ticks: {
        display: false,
        // min: 0,
        // max: 100,
        stepSize: 25,
      },
      // angleLines: {
      //   display: false,
      // },
      suggestedMin: 0,
      suggestedMax: 100,
    },
  },

  tooltips: {
    callbacks: {
      label: function (tooltipItem, data) {
        let label = data.labels[tooltipItem.index];
        console.log(label);
        let value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
        return " " + label + ": " + value + " %";
      },
    },
  },
});

Chart.register(...registerables);
</script>

<template>
  <Radar ref="radar" :options="chartOptions" :data="chartData" />
  <!-- <Radar :data="chartData" /> -->
</template>

<style scoped></style>
