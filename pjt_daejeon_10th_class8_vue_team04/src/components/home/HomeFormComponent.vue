<script setup>
import { ref, watch } from "vue";
// import { useKeywordStore } from "../../stores/counter";
// import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { getCandidates } from "../../api/House";
import { DatasetController } from "chart.js";
// router
const router = useRouter();

// global states
// const keywordStore = useKeywordStore();
// const { keyword } = storeToRefs(keywordStore);

// local states
let lastChecked = "";
const rawKeyword = ref("");

// XX도 XX시 XX구/군 XX읍/면/동
const refinedKeyword = ref({
  doName: "",
  siName: "",
  gugunName: "",
  eupmyeondongName: "",
});

const candidates = ref();
const candidate = ref(false);
const keyword = ref("");
const calendar = ref("");

const range = ref([
  {
    month: null,
    year: null,
  },
  {
    month: null,
    year: null,
  },
]);

console.log(range.value);
const dateFormat = (date) => {
  const year = date.getFullYear();
  const month = ("0" + (date.getMonth() + 1)).slice(-2);
  const day = ("0" + date.getDate()).slice(-2);
  return year + month + day;
};

const checkInput = async () => {
  if (lastChecked !== rawKeyword.value) {
    const keyword = rawKeyword.value;
    const keywordArray = keyword.split(" ");
    console.log(keywordArray);

    let doName = "",
      siName = "",
      gugunName = "",
      eupmyeondongName = "";

    keywordArray.forEach((keyword) => {
      if (keyword.endsWith("도")) doName = keyword;
      else if (keyword.endsWith("시")) siName = keyword;
      else if (keyword.match(/\\*(구|군)/)) gugunName = keyword;
      else if (keyword.match(/\\*(읍|면|동)/)) eupmyeondongName = keyword;
    });

    refinedKeyword.value = {
      doName,
      siName,
      gugunName,
      eupmyeondongName,
    };

    candidates.value = await getCandidates(refinedKeyword.value);

    candidate.value = !!candidates.value;

    lastChecked = rawKeyword.value;
  }
};

// 0.5초마다 검색어를 서버에 물어보고 후보 리스트 가져오기
const inputInterval = setInterval(checkInput, 500);

const onInputKeywordSubmit = () => {
  if (candidates.value && candidates.value[0]) {
    onCandidateClick(candidates.value[0]);
  }
};

const onCandidateClick = ({ dongCode }) => {
  clearInterval(inputInterval);
  checkInput();

  if (range.value.start === null) {
    range.value.start = new Date(Date.now() - 2629800000 * 3);
  }
  if (range.value.end === null) {
    range.value.end = new Date(Date.now());
  }

  router.push(
    `/search/${dongCode}` +
      "?startDate=" +
      yyyymmdd(range.value[0], false) +
      "&endDate=" +
      yyyymmdd(range.value[1], true)
  );
};

const yyyymmdd = ({ month, year }, isEndDate) => {
  let d = "01";
  if (isEndDate) {
    const date = new Date(year, month + 1, 0);
    d = String(date.getDate());
  }
  const m = ("0" + (month + 1)).slice(-2);
  return `${year || "2022"}${m || "04"}${d}`;
};

const allowedDates = [new Date(2015, 1, 1), new Date(2021, 4, 9)];

const formatMonth = ([start, end]) => {
  const startMonth = start.getMonth() + 1;
  const startYear = start.getFullYear();
  const endMonth = end.getMonth() + 1;
  const endYear = end.getFullYear();

  return `${startYear}년 ${startMonth}월부터 ${endYear}년 ${endMonth}월까지`;
};
</script>

<template>
  <div class="h-1/4 flex items-center">
    <!-- <lab el for="price" class="block text-sm font-medium leading-6 text-gray-900">Price</lab> -->
    <form class="flex items-center gap-4" @submit.prevent="onInputKeywordSubmit" ref="form">
      <div class="flex-column items-center relative w-96 rounded-md shadow-sm">
        <div class="inset-y-0 right-0 flex justify-center items-center">
          <VueDatePicker
            allowedDates
            :format="formatMonth"
            id="datePicker"
            auto-apply
            v-model="range"
            range
            month-picker
            :action-row="{
              showSelect: false,
              showCancel: true,
              showNow: false,
              showPreview: true,
            }"
          />
        </div>
        <div class="flex mt-2">
          <input
            id="keyword"
            v-model="rawKeyword"
            placeholder="지역명으로 검색하세요."
            type="text"
            class="block w-full rounded-md border-0 py-1.5 pl-7 pr-20 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
          />
          <v-menu
            :close-on-click="true"
            v-model="candidate"
            attach="#default-search"
            style="z-index: 9999999999999"
          >
            <div class="left-1/2 z-10 w-screen max-w-max" id="candidates">
              <div
                class="w-screen max-w-sm max-h-28 flex-auto overflow-y-scroll rounded-md bg-white text-sm leading-6 shadow-lg ring-1 ring-gray-900/5"
              >
                <div class="p-4">
                  <a
                    class="block hover:bg-gray-50 py-1"
                    v-for="candidate in candidates"
                    :key="candidate.dongCode"
                    :value="candidate.fullAddress"
                    @click="(event) => onCandidateClick(candidate)"
                    href="#"
                  >
                    {{ candidate.fullAddress }}
                  </a>
                </div>
              </div>
            </div>
          </v-menu>
        </div>
      </div>
      <button
        type="submit"
        class="flex justify-center rounded-md bg-indigo-600 px-5 py-4 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
      >
        검색
      </button>
    </form>
  </div>
</template>

<style scoped>
.dp__theme_light {
  --dp-font-family: "Pretendard-Regular" !important;
  --dp-font-weight: 500;
  --dp-primary-color: rgb(79 70 229) !important;
}
.dp__overlay_container {
  --dp-primary-color: rgb(79 70 229) !important;
}
#candidates {
  margin-top: 5.6rem;
}

.dp__input {
  border-radius: 0.375rem;
}
/* :root {
} */
</style>
