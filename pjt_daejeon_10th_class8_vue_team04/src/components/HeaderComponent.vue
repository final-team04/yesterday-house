<script setup>
/*************** imports ***************/

import {
  Disclosure,
  DisclosureButton,
  DisclosurePanel,
  Menu,
  MenuButton,
  MenuItem,
  MenuItems,
} from "@headlessui/vue";
import { Bars3Icon, BellIcon, XMarkIcon } from "@heroicons/vue/24/outline";

// routing
import router from "../router";

import SvgIcon from "@jamescoyle/vue-icon";
import { mdiAccount } from "@mdi/js";

// use global state
import { storeToRefs } from "pinia";
import { useMessage } from "../stores/counter";
import { useUserStore } from "../stores/counter";

// requests
import { logout } from "../api/User";
import { remove } from "../api/User";

/*************** declaration ***************/
// global states
const user = storeToRefs(useUserStore()).user;
const { message, toast } = storeToRefs(useMessage());

const navigation = [
  { name: "Dashboard", href: "#", current: true },
  { name: "Team", href: "#", current: false },
  { name: "Projects", href: "#", current: false },
  { name: "Calendar", href: "#", current: false },
];

/*************** functions ***************/
const onLogoutClick = async () => {
  await logout();
  user.value = null;

  message.value = "로그아웃 되었습니다.";
  toast.value = true;
  router.push("/");
};

const onRemoveClick = async () => {
  await remove(user.value.userId);

  user.value = null;

  message.value = "회원 탈퇴가 완료되었습니다.";
  toast.value = true;

  router.push("/");
};
</script>

<template>
  <Disclosure as="nav" class="bg-gray-800">
    <div class="mx-auto w-auto px-8 sm:px-6 lg:px-8">
      <div class="relative flex h-16 items-center justify-between">
        <!-- <div class="absolute inset-y-0 left-0 flex items-center sm:hidden">
          Mobile menu button
          <DisclosureButton
            class="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
          >
            <span class="absolute -inset-0.5" />
            <span class="sr-only">Open main menu</span>
            <Bars3Icon v-if="!open" class="block h-6 w-6" aria-hidden="true" />
            <XMarkIcon v-else class="block h-6 w-6" aria-hidden="true" />
          </DisclosureButton>
        </div> -->
        <div class="flex flex-shrink-0 items-center pl-4">
          <router-link to="/" class="flex items-center h-6">
            <!-- <img
              class="h-8 w-auto"
              src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=500"
              alt="Your Company"
            /> -->
            <svg
              version="1.1"
              id="Layer_1"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              x="0px"
              y="0px"
              class="h-8 w-8 me-2"
              viewBox="0 0 595.28 595.28"
              enable-background="new 0 0 595.28 595.28"
              xml:space="preserve"
            >
              <path
                id="color_x5F_1"
                fill="rgb(129 140 248)"
                d="M527.467,557.286V202.079c0-7.045-4.452-13.324-11.098-15.664l-109.913-38.59V79.241    c0-7.045-4.452-13.323-11.098-15.663L238.721,8.583c-0.349-0.122-0.711-0.137-1.063-0.235c-0.575-0.159-1.142-0.283-1.729-0.38    c-7.904-1.313-14.053,3.894-20.176,7.979c-6.685,4.459-13.37,8.918-20.053,13.376c-14.921,9.953-29.84,19.904-44.76,29.856    c-3.125,2.085-6.25,4.167-9.373,6.253c-4.614,3.08-7.391,8.261-7.391,13.81v72.955L80.105,188.27    c-4.625,3.08-7.392,8.261-7.392,13.81v355.207h-45.61v33.194h545.973v-33.194H527.467z M376.524,557.286H347.75v-346.33    l28.774-19.197V557.286z M167.371,88.123l49.252-32.854v502.017h-49.252V88.123z M105.908,210.956l28.269-18.857v365.188h-28.269    V210.956z"
              ></path>
            </svg>
            <p id="main-title" class="font-normal text-xl text-white">어제의집</p>
          </router-link>
        </div>

        <div
          v-if="!user"
          class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0"
        >
          <div class="sm:ml-6 sm:block">
            <div class="flex space-x-4">
              <router-link
                to="/login"
                class="text-white hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium"
                >로그인</router-link
              >
            </div>
          </div>
          <div class="sm:ml-6 sm:block">
            <div class="flex space-x-4">
              <router-link
                to="/register"
                class="text-white hover:bg-gray-700 hover:text-white rounded-md px-3 py-2 text-sm font-medium"
                >회원가입</router-link
              >
            </div>
          </div>
        </div>
        <div
          v-else
          class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0"
        >
          <!-- Profile dropdown -->
          <Menu as="div" class="relative ml-3">
            <div>
              <MenuButton
                class="relative flex rounded-full bg-gray-800 text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"
              >
                <span class="absolute -inset-1.5" />
                <span class="sr-only">Open user menu</span>
                <img
                  class="h-8 w-8 rounded-full"
                  src="https://w7.pngwing.com/pngs/215/58/png-transparent-computer-icons-google-account-scalable-graphics-computer-file-my-account-icon-rim-123rf-symbol.png"
                  alt=""
                />
              </MenuButton>
            </div>
            <transition
              enter-active-class="transition ease-out duration-100"
              enter-from-class="transform opacity-0 scale-95"
              enter-to-class="transform opacity-100 scale-100"
              leave-active-class="transition ease-in duration-75"
              leave-from-class="transform opacity-100 scale-100"
              leave-to-class="transform opacity-0 scale-95"
            >
              <MenuItems
                class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
              >
                <MenuItem>
                  <router-link to="/edit">
                    <button class="block px-4 py-2 text-sm text-gray-700">정보 수정하기</button>
                  </router-link>
                </MenuItem>
                <MenuItem>
                  <button class="block px-4 py-2 text-sm text-gray-700" @click="onLogoutClick">
                    로그아웃
                  </button>
                </MenuItem>
                <MenuItem>
                  <button class="block px-4 py-2 text-sm text-red-700" @click="onRemoveClick">
                    회원 탈퇴
                  </button>
                </MenuItem>
              </MenuItems>
            </transition>
          </Menu>
        </div>
      </div>
    </div>

    <!-- <DisclosurePanel class="sm:hidden">
    <div class="space-y-1 px-2 pb-3 pt-2">
      <DisclosureButton
        v-for="item in navigation"
        :key="item.name"
        as="a"
        :href="item.href"
        :class="[
          item.current
            ? 'bg-gray-900 text-white'
            : 'text-gray-300 hover:bg-gray-700 hover:text-white',
          'block rounded-md px-3 py-2 text-base font-medium',
        ]"
        :aria-current="item.current ? 'page' : undefined"
        >{{ item.name }}</DisclosureButton
      >
    </div>
  </DisclosurePanel> -->
  </Disclosure>
</template>

<style scoped>
@font-face {
  font-family: "JalnanGothic";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_231029@1.1/JalnanGothic.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

#main-title {
  font-family: "JalnanGothic";
}
</style>
