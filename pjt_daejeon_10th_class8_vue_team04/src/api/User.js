import { localAxios } from "../util/Axios";

const userRequest = localAxios();

const USER = "/user";
const LOGIN = "/login";
const LOGOUT = "/logout";

export const getUserInfo = async () => {
  return await userRequest.get(USER);
};
export const login = async (data) => {
  return await userRequest.post(LOGIN, data);
};

export const register = async (data) => {
  return await userRequest.post(USER, data);
};

export const logout = async () => {
  return await userRequest.get(LOGOUT);
};

export const edit = async (data) => {
  return await userRequest.put(USER, data);
};

export const remove = async (userId) => {
  return await userRequest.delete(`${USER}/${userId}`);
};
