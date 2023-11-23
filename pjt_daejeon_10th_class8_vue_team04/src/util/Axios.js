import axios from "axios";

// local vue api axios instance
axios.defaults.withCredentials = true;

const LOCAL = import.meta.VITE_VUE_API_URL;
const MYIP = "192.168.130.64:80";

export const localAxios = () => {
  const instance = axios.create({
    // baseURL: `http://${MYIP}/`,
    baseURL: `http://localhost/`,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
};
