<script setup>
import { ref, watch, onMounted, toRefs } from "vue";
///////////////////////////////////////////////////////////////////////////////////////////
// 초기 설정

const created = ref(false);
const roadviewContainer = ref(null);

const props = defineProps({
  data: Object,
});

const { data } = toRefs(props);

var roadview;
var roadviewClient;
var position;

const initRoadView = async () => {
  roadview = new kakao.maps.Roadview(roadviewContainer.value); //로드뷰 객체
  roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
  setRoadView();
};

const setRoadView = async () => {
  position = new kakao.maps.LatLng(data.value.lat, data.value.lng);

  // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
  roadviewClient.getNearestPanoId(position, 50, function (panoId) {
    roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
  });
};

///////////////////////////////////////////////////////////////////////////////////////////
// 지도 설정

onMounted(async () => {
  if (window.kakao && window.kakao.maps) {
    await initRoadView();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=742acb4b232b1a6031779db753d6950b&libraries=services,clusterer,drawing&autoload=false`;
    /* global kakao */
    script.onload = async () => await kakao.maps.load(async () => await initRoadView());
    document.head.appendChild(script);
  }

  created.value = true;
});

watch(data, () => {
  setRoadView();
});
</script>

<template>
  <div ref="roadviewContainer" style="width: 700px; height: 700px"></div>
</template>

<style scoped></style>
