<script setup>
import { ref, watch } from "vue";
import { useRoute } from "vue-router";
import { _getAptByDong, _getAptByName } from "../../api/House.js";
import { storeToRefs } from "pinia";
import { useMap, useAptInfo } from "../../stores/counter";
import HouseListItemComponent from "./HouseListItemComponent.vue";

// useRoute와 useRouter는 setup 함수 내에서 사용되어야 합니다.
const route = useRoute();
const dongCode = ref(route.params.keyword);
const dongName = ref("");

const { startDate, endDate } = route.query;

const list = ref();
const check = ref(false);
const searchAptName = ref("");

// const emits = defineEmits(["getAptListData", "getAptData"]);
const emits = defineEmits(["pickApt"]);

const { created } = storeToRefs(useMap());
const { aptList } = storeToRefs(useAptInfo());

watch(
  () => created.value,
  async () => {
    if (created.value) {
      const response = await _getAptByDong({
        dongCode: dongCode.value,
        startDate,
        endDate,
      });

      // 피니아 => 리스폰스 담아보자
      aptList.value = response;

      list.value = response;
      check.value = true;
      dongName.value = response[0].dong;

      // emits("getAptListData", response);

      created.value = false;
    }
  },
  { deep: true }
);

const pickApt = (data) => {
  emits("pickApt", data);
};

// 아파트 이름 검색
const aptSearch = async () => {
  const response = await _getAptByName({
    apartmentName: searchAptName.value,
    dongCode: dongCode.value,
    startDate,
    endDate,
  });
  list.value = response;

  aptList.value = response;

  // emits("getAptListData", response);
};
</script>

<template>
  <ul
    role="list"
    v-if="list"
    class="w-80 h-full bg-white p-4 divide-y divide-gray-100 overflow-auto"
  >
    <li>
      <div class="flex flex-column mb-5 items-start gap-x-6">
        <div>
          <h3 class="text-xl mb-1 font-semibold leading-7 tracking-tight text-gray-900">
            {{ dongName }}
          </h3>
          <p class="text-sm mb-3 font-semibold leading-6 text-indigo-600">
            {{ `${dongName} 주변의 정보를 확인하세요.` }}
          </p>
        </div>
        <form @submit.prevent="aptSearch">
          <input
            v-model="searchAptName"
            placeholder="아파트 이름으로 검색하세요."
            type="text"
            class="block w-full rounded-md border-0 py-1.5 pl-3 pr-20 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
          />
        </form>
      </div>
    </li>
    <div v-if="list">
      <HouseListItemComponent
        v-for="item in list"
        :data="item"
        :key="item.no"
        @pick-apt="pickApt"
      />
    </div>
  </ul>
  <ul role="list" v-else class="w-80 h-full bg-white p-4 divide-y divide-gray-100 overflow-auto">
    <li>
      <p class="text-base text-center mt-3 font-semibold leading-6 text-indigo-600">
        거래 내역이 없습니다.
      </p>
      <router-link to="/">
        <p class="text-sm text-center mt-3 font-semibold leading-6 text-black">홈으로 가기</p>
      </router-link>
    </li>
  </ul>
  <!-- 
  <v-navigation-drawer width="300" v-if="check" height="inherit">
    <v-list-item :title="dongName" :subtitle="`${dongName} 정보`"> </v-list-item>
    <v-divider />
    <v-list-item>
      <v-text-field label="Label" variant="outlined" v-model="searchAptName"></v-text-field>
      <v-btn @click="aptSearch">검색</v-btn>
    </v-list-item>
    <v-divider />
    <Houselistitemcomponent v-for="item in list" :data="item" :key="item.no" @pick-apt="pickApt" />
  </v-navigation-drawer> -->
</template>

<style scoped>
::-webkit-scrollbar {
  display: none;
}
</style>
