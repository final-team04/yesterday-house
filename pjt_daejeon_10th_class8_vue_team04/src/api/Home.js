// 백엔드로 뉴스 요청
import { localAxios } from "../util/Axios";

const newsRequest = localAxios();

const NEWS = "/news";

export const getNews = async () => {
  const response = await newsRequest.get(NEWS);
  return response.data;
};
