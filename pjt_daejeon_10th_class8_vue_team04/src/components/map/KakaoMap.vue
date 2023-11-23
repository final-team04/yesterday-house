<script setup>
import { storeToRefs } from "pinia";
import { ref, watch, onMounted } from "vue";
import { useMap, useAptInfo } from "../../stores/counter";

const { created } = storeToRefs(useMap());
const { aptList, inCircleList, inCircleInfo } = storeToRefs(useAptInfo());

var map;
//
const categorys = ["MT1", "CS2", "PS3", "SC4", "SW8", "HP8", "PM9", "CT1", "BK9", "PO3"];

const positions = ref([]);
const markers = ref([]);
const overlays = ref([]);
// 반경 1km 안에 마커들
const inCircleMarkers = ref([]);
// const searchNearbyPlacesMarkers = ref([]);

var myCircle;

const props = defineProps({
  // list: Array,
  data: Object,
  // selectStation: Object,
});

const emits = defineEmits(["pickApt"]);

///////////////////////////////////////////////////////////////////////////////////////////
// 리스트 초기화
let temp = [];
watch(
  () => aptList.value,
  () => {
    positions.value = [];

    temp = aptList.value;
    for (let i = 0; i < temp.length; i++) {
      temp[i].title = temp[i].apartmentName + "." + i;
      const obj = {
        latlng: new kakao.maps.LatLng(temp[i].lat, temp[i].lng),
        title: temp[i].title,
      };

      positions.value.push(obj);
    }

    if (myCircle != null) {
      myCircle.setMap(null);
    }

    loadMarkers();
  },
  { deep: true }
);

///////////////////////////////////////////////////////////////////////////////////////////
// 마커 선택, 1km 이내 거래내역
watch(
  () => props.data,
  async () => {
    var center = new kakao.maps.LatLng(props.data.lat, props.data.lng);
    myCircle.setPosition(center);
    myCircle.setMap(map);
    map.panTo(center);

    var radius = 1000; // 1킬로미터
    var line = new kakao.maps.Polyline();

    inCircleMarkers.value = [];

    for (let i = 0; i < markers.value.length; i++) {
      const marker = markers.value[i];

      if (marker.getTitle() !== props.data.title) {
        const path = [marker.getPosition(), center];
        line.setPath(path);

        const dist = line.getLength();

        if (dist <= radius) {
          inCircleMarkers.value.push(aptList.value[i]);
        }
      }
    }

    await searchNearbyInformation();

    inCircleList.value = inCircleMarkers.value;

    // emits("getInCircleMarkers", category_data);
  },
  { deep: true }
);

///////////////////////////////////////////////////////////////////////////////////////////
// 주변 시설 검색
const searchNearbyInformation = async () => {
  const location = new kakao.maps.LatLng(props.data.lat, props.data.lng);
  let category_data = {};

  const places = new kakao.maps.services.Places();
  const searchCategory = (categoryCode) => {
    return new Promise((resolve) => {
      const callback = function (result, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
          resolve({ categoryCode, totalCount: pagination.totalCount });
        }
      };

      places.categorySearch(categoryCode, callback, {
        location: location,
        radius: 1000,
        page: 1,
      });
    });
  };

  const categoryResults = await Promise.all(categorys.map((category) => searchCategory(category)));

  categoryResults.forEach((result) => {
    category_data[result.categoryCode] = result.totalCount;
  });

  inCircleInfo.value = category_data;
};

///////////////////////////////////////////////////////////////////////////////////////////
// 지도 설정
onMounted(async () => {
  if (window.kakao && window.kakao.maps) {
    await initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=742acb4b232b1a6031779db753d6950b&libraries=services,clusterer,drawing&autoload=false`;
    /* global kakao */
    script.onload = async () => await kakao.maps.load(async () => await initMap());
    document.head.appendChild(script);
  }
});

///////////////////////////////////////////////////////////////////////////////////////////
// 초기 설정
const initMap = async () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 5,
  };
  map = await new kakao.maps.Map(container, options);
  created.value = true;
};

const closeOverlay = () => {
  for (const key in overlays.value) {
    overlays.value[key].setMap(null);
  }
};

const roadAdress = (aptData) => {
  let str = `${aptData.roadName} ${aptData.roadNameBonbun}`;
  if (!aptData.roadNameBubun == 0) {
    str += `-${aptData.roadNameBubun}`;
  }
  return str;
};

///////////////////////////////////////////////////////////////////////////////////////////
// 마커 생성
const loadMarkers = () => {
  deleteMarkers();

  myCircle = new kakao.maps.Circle({
    center: new kakao.maps.LatLng(37.566826, 126.978656),
    radius: 1000,
    strokeWeight: 5,
    strokeColor: "#00F",
    strokeOpacity: 0.7,
    fillColor: "#00F",
    fillOpacity: 0.3,
  });

  const positionsArray = positions.value;

  for (let i = 0; i < positionsArray.length; i++) {
    const position = positionsArray[i];
    const marker = new kakao.maps.Marker({
      map: map,
      position: position.latlng,
      title: position.title,
      clickable: true,
    });

    var content = `
    <div
    class="relative bottom-24 close w-fit h-24 p-2 max-w-md flex flex-column justify-center text-center overflow-hidden rounded-3xl bg-white text-sm leading-6 shadow-lg ring-1 ring-gray-900/5 hover:bg-gray-100"
    >
      <h3 class="title mx-2 text-sm font-semibold leading-7 text-gray-900">${
        aptList.value[i].apartmentName
      }</h3>
      <p class="ellipsis mt-1 mb-2 text-xs text-gray-600">${roadAdress(aptList.value[i])}</p>
    </div>`;
    // ${aptList.value[i].fullAddress} ${aptList.value[i].jibun}

    var overlay = new kakao.maps.CustomOverlay({
      content: content,
      map: null,
      position: marker.getPosition(),
    });

    overlays.value.push(overlay);

    kakao.maps.event.addListener(marker, "click", () => {
      // 여기서 피니아 처리
      // pickAptData.value = aptList.value[i];
      console.log(aptList.value[i]);
      closeOverlay();
      overlays.value[i].setMap(map);
      let closeBtn = document.getElementsByClassName("close")[0];
      closeBtn.addEventListener("click", closeOverlay);
      emits("pickApt", aptList.value[i]);
    });
    markers.value.push(marker);
  }

  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
  map.setLevel(5);
};

///////////////////////////////////////////////////////////////////////////////////////////
// 마커 삭제
const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
  markers.value = [];
  overlays.value = [];
};

///////////////////////////////////////////////////////////////////////////////////////////
// 주변 상권 삭제
// const deleteSearchNearbyPlacesMarkers = () => {
//   if (searchNearbyPlacesMarkers.value.length > 0) {
//     searchNearbyPlacesMarkers.value.forEach((marker) => marker.setMap(null));
//   }
//   searchNearbyPlacesMarkers.value = [];
// };

///////////////////////////////////////////////////////////////////////////////////////////
</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  height: 100%;
}

.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 120px;
  margin-left: -144px;
  overflow: hidden;
  font-size: 12px;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}

.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 25px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>
