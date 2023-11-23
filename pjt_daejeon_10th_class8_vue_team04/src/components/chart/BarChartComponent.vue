<script setup>
import { ref, watch, toRefs } from "vue";
import { Bar } from "vue-chartjs";
import { Chart, registerables } from "chart.js/auto";

const props = defineProps({
  data: Object,
});

const { data } = toRefs(props);
const bar = ref();

watch(data, () => {
  const newDataArray = [
    data.value.MT1,
    data.value.CS2,
    data.value.PS3,
    data.value.SC4,
    data.value.SW8,
    data.value.HP8,
    data.value.PM9,
    data.value.CT1,
    data.value.BK9,
    data.value.PO3,
  ];

  bar.value.chart.data.datasets[0].data = newDataArray;
  bar.value.chart.update();
});

const chartData = {
  labels: [
    "대형마트",
    "편의점",
    "어린이집(유치원)",
    "학교",
    "지하철",
    "병원",
    "약국",
    "문화시설",
    "은행",
    "공공기관",
  ],
  datasets: [
    {
      label: "1km 반경 시설",
      data: [
        data.value.MT1,
        data.value.CS2,
        data.value.PS3,
        data.value.SC4,
        data.value.SW8,
        data.value.HP8,
        data.value.PM9,
        data.value.CT1,
        data.value.BK9,
        data.value.PO3,
      ],
      backgroundColor: [
        "rgba(255, 99, 132, 0.2)",
        "rgba(255, 159, 64, 0.2)",
        "rgba(255, 205, 86, 0.2)",
        "rgba(75, 192, 192, 0.2)",
        "rgba(54, 162, 235, 0.2)",
        "rgba(153, 102, 255, 0.2)",
        "rgba(201, 203, 207, 0.2)",
      ],
      borderColor: [
        "rgb(255, 99, 132)",
        "rgb(255, 159, 64)",
        "rgb(255, 205, 86)",
        "rgb(75, 192, 192)",
        "rgb(54, 162, 235)",
        "rgb(153, 102, 255)",
        "rgb(201, 203, 207)",
      ],
      borderWidth: 1,
    },
  ],
};

const chartOptions = ref({
  responsive: true,
  scales: {
    // r: {
    //   ticks: {
    //     display: false,
    //   },
    // },
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
  <Bar ref="bar" :options="chartOptions" :data="chartData"></Bar>
</template>

<style scoped></style>
