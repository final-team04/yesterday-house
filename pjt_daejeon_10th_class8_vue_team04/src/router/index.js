import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("../views/HomeView.vue"),
    },
    {
      path: "/search/:keyword",
      name: "search",
      component: () => import("../views/HouseView.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/LoginView.vue"),
    },
    {
      path: "/register",
      name: "register",
      component: () => import("../views/RegisterView.vue"),
    },
    {
      path: "/edit",
      name: "edit",
      component: () => import("../views/EditView.vue"),
    },
    {
      path: "/:notFound",
      name: "404",
      component: () => import("../views/404View.vue"),
    },
  ],
});

export default router;
