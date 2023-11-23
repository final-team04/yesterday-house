import { localAxios } from "../util/Axios.js";

const local = localAxios();

const KEYWORD = "/keyword";

export const _getAptByDong = async ({ dongCode, startDate, endDate }) => {
  const response = await local.get(
    `/house/dong?dongCode=${dongCode}&startDate=${startDate}&endDate=${endDate}`
  );
  if (!response.data) throw new Error("찾으시는 지역 혹은 기간에 거래 내역이 없습니다.");
  return response.data;
};

export const _getAptByName = async ({ apartmentName, dongCode, startDate, endDate }) => {
  const response = await local.get(
    `/house/apt?apartmentName=${apartmentName}&dongCode=${dongCode}&startDate=${startDate}&endDate=${endDate}`
  );
  if (!response.data) throw new Error("찾으시는 지역 혹은 기간에 거래 내역이 없습니다.");
  return response.data;
};

export const getCandidates = async ({ doName, siName, gugunName, eupmyeondongName }) => {
  const response = await local.get(
    `/house${KEYWORD}?doName=${doName}&siName=${siName}&gugunName=${gugunName}&eupmyeondongName=${eupmyeondongName}`
  );
  // if (!response.data) throw new Error("찾으시는 지역 혹은 기간에 거래 내역이 없습니다.");
  return response.data;
};
