<script setup>
import BoardItemComponent from "../board/BoardItemComponent.vue";
import BoardCreateComponent from "../board/BoardCreateComponent.vue";
import OtherHouseDetailComponent from "./OtherHouseDetailComponent.vue";
import { _getBoardList, _deleteBoard } from "../../api/Board";
import { ref, watch, toRefs } from "vue";
import { storeToRefs } from "pinia";
import { useAptInfo, useUserStore } from "../../stores/counter";
import ChartComponent from "../chart/ChartComponent.vue";
import RoadViewComponent from "../map/RoadViewComponent.vue";
import { useRouter } from "vue-router";

const router = useRouter();

const boardList = ref();
const inCircleMarkers = ref(); // {}
const { inCircleList } = storeToRefs(useAptInfo()); // []

const props = defineProps({
  data: Object,
  createBoardCheck: Boolean,
});
const { user } = storeToRefs(useUserStore());

const { data } = toRefs(props);

const board = ref(false);

const emits = defineEmits([
  "getBoardData",
  "closeAll",
  "createBoard",
  "modifyBoard",
  "deleteBoard",
]);

const roadview = ref(false);

watch(
  data,
  () => {
    getBoardList(data.value.aptCode);
  },
  { deep: true }
);

watch(
  () => props.createBoardCheck,
  () => {
    getBoardList(data.value.aptCode);
  },
  { deep: true }
);

watch(
  () => inCircleList.value,
  () => {
    inCircleMarkers.value = {};

    const markers = inCircleList.value;

    // for문을 사용한 렌더링

    for (let i = 0; i < markers.length; i++) {
      if (!inCircleMarkers.value[`${markers[i].apartmentName}`]) {
        inCircleMarkers.value[`${markers[i].apartmentName}`] = [markers[i]];
      } else {
        inCircleMarkers.value[`${markers[i].apartmentName}`].push(markers[i]);
      }
    }
  },
  { deep: true }
);

const getBoardList = async (aptCode) => {
  const result = await _getBoardList(aptCode);
  boardList.value = result;
};

getBoardList(data.value.aptCode);

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

const dealDate = (aptData) => ` ${aptData.dealYear}년 ${aptData.dealMonth}월 ${aptData.dealDay}일`;

const jibunAdress = (aptData) => `${aptData.fullAddress} ${aptData.jibun}`;

const roadAdress = (aptData) => {
  let str = `${aptData.roadName} ${aptData.roadNameBonbun}`;
  if (!aptData.roadNameBubun == 0) {
    str += `-${aptData.roadNameBubun}`;
  }
  return str;
};

const area = (area) => {
  return (parseFloat(area) / 3.3058).toFixed(2) + " 평";
};

const title = () => {
  let count = 0;
  for (const key in inCircleMarkers.value) {
    count += inCircleMarkers.value[key].length;
  }
  return "주변 아파트 거래 내역 : " + count + "건";
};

const closeAll = () => {
  emits("closeAll");
};

const createBoard = () => {
  emits("createBoard", data.value);
};

const getBoardData = (data) => {
  emits("getBoardData", data);
};

const modifyBoard = (data) => {
  emits("modifyBoard", data);
};

const deleteBoard = async (bno) => {
  await _deleteBoard(bno);
  getBoardList(data.value.aptCode);
};
</script>

<!-- 선택가능한 카드는 hover:bg-gray-100 hover:cursor-pointer 를 적용 -->
<template>
  <ul role="list" class="w-80 h-full bg-white p-4 divide-y divide-gray-100 overflow-auto">
    <!-- 간략한 매물 정보 -->
    <div
      class="flex justify-between px-1 rounded-lg gap-x-6 py-5 hover:bg-gray-100 hover:cursor-pointer"
    >
      <v-dialog v-model="roadview" activator="parent" width="auto">
        <v-card> <RoadViewComponent :data="data" /></v-card>
      </v-dialog>
      <div class="flex min-w-0 gap-x-4">
        <div class="min-w-0 flex-auto">
          <h3 class="text-xl mb-2 font-semibold leading-7 text-gray-900">
            {{ props.data.apartmentName }}
          </h3>
          <p class="mt-1 truncate text-xs leading-5 text-gray-500">
            {{ `${dealDate(data)}에 거래되었어요.` }}
          </p>
        </div>
      </div>
      <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
        <p class="text-sm leading-6 text-gray-900">{{ formatDealAmount(data.dealAmount) }}</p>
      </div>
    </div>

    <!-- 차트 -->
    <div class="mt-3 border-t border-gray-100 px-1 gap-x-6 pt-3">
      <div class="rounded-lg hover:bg-gray-100 hover:cursor-pointer">
        <ChartComponent v-if="inCircleList" :data="data" />
      </div>
    </div>

    <!-- 상세정보 -->
    <div class="mt-3 border-t border-gray-100">
      <h3 class="text-xl mt-5 mb-2 font-semibold leading-7 text-gray-900">상세정보</h3>

      <li class="flex justify-between px-1 rounded-lg gap-x-6 py-1">
        <div class="flex min-w-0 gap-x-4">
          <div class="min-w-0 flex-auto">
            <p class="text-sm font-semibold leading-6 text-gray-900">
              {{ `준공연도` }}
            </p>
          </div>
        </div>
        <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
          <p class="text-sm leading-6 text-gray-900">{{ data.buildYear }}</p>
        </div>
      </li>

      <li class="flex justify-between px-1 rounded-lg gap-x-6 py-1">
        <div class="flex min-w-0 gap-x-4">
          <div class="min-w-0 flex-auto">
            <p class="text-sm font-semibold leading-6 text-gray-900">
              {{ `거래일` }}
            </p>
          </div>
        </div>
        <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
          <p class="text-sm leading-6 text-gray-900">{{ dealDate(data) }}</p>
        </div>
      </li>

      <li class="flex justify-between px-1 rounded-lg gap-x-6 py-1">
        <div class="flex min-w-0 gap-x-4">
          <div class="min-w-0 flex-auto">
            <p class="text-sm font-semibold leading-6 text-gray-900">
              {{ `도로명주소` }}
            </p>
          </div>
        </div>
        <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
          <p class="text-sm leading-6 text-gray-900">{{ roadAdress(data) }}</p>
        </div>
      </li>

      <li class="flex justify-between px-1 rounded-lg gap-x-6 py-1">
        <div class="flex min-w-0 gap-x-4">
          <div class="min-w-0 flex-auto">
            <p class="text-sm font-semibold leading-6 text-gray-900">
              {{ `정보` }}
            </p>
          </div>
        </div>
        <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
          <p
            class="text-sm leading-6 text-gray-900"
            v-html="`${data.floor}층, ${data.area}m<sup>2</sup> (${area(data.area)})`"
          ></p>
        </div>
      </li>
    </div>

    <!-- 게시판 -->
    <div class="mt-3 border-t border-gray-100 max-h-96">
      <h3 class="text-xl mt-5 mb-0 font-semibold leading-7 text-gray-900">매물 이모저모</h3>
      <perfect-scrollbar v-if="boardList" class="mt-2 overflow-auto max-h-60 p-1 border rounded-md">
        <BoardItemComponent
          v-for="item in boardList"
          :board="item"
          :key="item.bno"
          @get-board-data="getBoardData"
          @modify-board="modifyBoard"
          @delete-board="deleteBoard"
        />
      </perfect-scrollbar>
      <template v-else>
        <li class="px-1 rounded-lg gap-x-6 py-1 text-gray-400 text-center">
          <div class="min-w-0 mt-1 gap-x-4">
            <div class="min-w-0">
              <p class="text-sm mb-1 font-semibold leading-6">아직 이 매물에 대한 글이 없어요.</p>
              <p class="text-sm font-semibold leading-6">여러분의 이야기를 들려주세요.</p>
            </div>
          </div>
        </li>
      </template>

      <button
        v-if="user"
        id="createBoardButton"
        type="submit"
        class="w-full mt-2 rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
      >
        글쓰기
      </button>

      <button
        v-else
        class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
        @click="() => router.push('/login')"
      >
        로그인 후 글쓰기
      </button>
    </div>

    <!-- 근처 매물 -->
    <div class="mt-3 border-t border-gray-100 max-h-[40rem]">
      <h3 class="text-xl mt-5 mb-2 font-semibold leading-7 text-gray-900">근처 다른 매물</h3>
      <perfect-scrollbar
        v-if="inCircleList"
        class="mt-2 overflow-auto max-h-[35rem] p-1 border rounded-md"
      >
        <OtherHouseDetailComponent
          v-for="(apt, apartmentName) in inCircleMarkers"
          :key="apartmentName"
          :apartmentName="apartmentName"
          :apt="apt"
          :data="props.data"
        ></OtherHouseDetailComponent>
      </perfect-scrollbar>
    </div>
  </ul>
</template>

<!-- 
<template>
  <v-navigation-drawer width="300" location="end">
    <v-btn>
      로드뷰 보기
      <v-dialog v-model="roadview" activator="parent" width="auto">
        <v-card> <RoadViewComponent :data="data" /></v-card>
      </v-dialog>
    </v-btn>

    <ChartComponent v-if="inCircleList" :data="data" />

    <v-btn @click="closeAll"> X </v-btn>
    <v-list-item title="아파트 거래 정보"></v-list-item>
    <v-divider></v-divider>

    <p>아파트 : {{ data.apartmentName }}</p>
    <p>준공년도 : {{ data.buildYear }}년</p>
    <p>거래금액 : {{ formatDealAmount(data.dealAmount) }}</p>
    <p>거래일자 : {{ dealDate(data) }}</p>
    <p>지번 : {{ jibunAdress(data) }}</p>
    <p>도로명주소 : {{ roadAdress(data) }}</p>
    <p>층수 : {{ data.floor }} 층</p>
    <p>면적 : {{ data.area }}m<sup>2</sup> ({{ area(data.area) }})</p>

    <v-divider></v-divider>
    <v-list-item title="아파트 게시판"></v-list-item>
    <v-btn @click="createBoard"> 글쓰기 </v-btn>
    <v-divider></v-divider>
    <BoardItemComponent
      v-for="item in boardList"
      :board="item"
      :key="item.bno"
      @get-board-data="getBoardData"
      @modify-board="modifyBoard"
      @delete-board="deleteBoard"
    />
    <v-list-item :title="title()"></v-list-item>
    <v-divider></v-divider>
    <template v-for="(apt, apartmentName) in inCircleMarkers" :key="apartmentName">
      <OtherHouseDetailComponent
        :apartmentName="apartmentName"
        :apt="apt"
        :data="props.data"
      ></OtherHouseDetailComponent>
    </template>
    <v-divider></v-divider>
  </v-navigation-drawer>
</template>
 -->
<style scoped>
.ps__rail-y {
  right: -3px;
}
</style>
