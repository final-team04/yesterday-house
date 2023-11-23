package com.ssafy.news.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin("*")
//@CrossOrigin("*")
public class NewsController {
    final String NEWS_API_CLIENT_ID = "RWrkoHZoI73uLqEQFVLr"; // 애플리케이션 클라이언트 아이디
    final String NEWS_API_CLIENT_SECRET = "SO7KUs0ITb"; // 애플리케이션 클라이언트 시크릿

    final String SUMMARY_API_CLIENT_ID = "dpy4nftth8";
    final String SUMMARY_API_CLIENT_SECRET = "XfeljTfXR8bgUP0FkLqVDwhXKiPgjXsCxYNaG8oT";


    private String newsApiUrl = null, summaryApiUrl = null;

    private final Map<String, String> newsApiRequestHeaders, summaryApiRequestHeaders;

    public NewsController() throws UnsupportedEncodingException {
        newsApiRequestHeaders = new HashMap<>();
        newsApiRequestHeaders.put("X-Naver-Client-Id", NEWS_API_CLIENT_ID);
        newsApiRequestHeaders.put("X-Naver-Client-Secret", NEWS_API_CLIENT_SECRET);

        newsApiUrl = "https://openapi.naver.com/v1/search/news?query=" + URLEncoder.encode("부동산", "UTF-8");
        summaryApiUrl = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";

        summaryApiRequestHeaders = new HashMap<>();
        summaryApiRequestHeaders.put("X-NCP-APIGW-API-KEY-ID", SUMMARY_API_CLIENT_ID);
        summaryApiRequestHeaders.put("X-NCP-APIGW-API-KEY", SUMMARY_API_CLIENT_SECRET);
        summaryApiRequestHeaders.put("Content-Type", "application/json");

    }

    @GetMapping("/news")
    @ResponseBody
    private String fetch(HttpSession session) throws ParseException {
//        System.out.println(session.getAttribute("userInfo"));
        HttpURLConnection newsApiConnection = connect(newsApiUrl);
        try {
            newsApiConnection.setRequestMethod("GET");
            for (Map.Entry<String, String> header : newsApiRequestHeaders.entrySet()) {
                newsApiConnection.setRequestProperty(header.getKey(), header.getValue());
            }

            newsApiConnection.setDoOutput(true);

//            System.out.println(newsApiConnection.getInputStream());

            int responseCode = newsApiConnection.getResponseCode();
            String body;
//            System.out.println("summaryApi response Code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                body = readBody(newsApiConnection.getInputStream());
//                return summarize(body);
                return body;
            } else { // 오류 발생
                body = readBody(newsApiConnection.getErrorStream());
                return body;
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            newsApiConnection.disconnect();
        }
    }

    private String summarize(String body) throws ParseException, IOException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
        JSONArray jsonArray = (JSONArray) jsonObject.get("items");

        for (Object obj : jsonArray) {
            JSONObject item = (JSONObject) obj;

            String description = (String) item.get("description");
            System.out.println("description: " + description);
            String result = "";

            // API 호출해서 요약 후 바꾸기

            HttpURLConnection summaryApiConnection = connect(summaryApiUrl);
            summaryApiConnection.setRequestMethod("POST");
            summaryApiConnection.setDoOutput(true);
            // 헤더 설정
            for (Map.Entry<String, String> header : summaryApiRequestHeaders.entrySet()) {
                summaryApiConnection.setRequestProperty(header.getKey(), header.getValue());
            }

            // 바디 설정

            OutputStreamWriter writer = new OutputStreamWriter(summaryApiConnection.getOutputStream(), StandardCharsets.UTF_8);

            JSONObject data = new JSONObject();
            JSONObject document = new JSONObject();
            JSONObject option = new JSONObject();

            document.put("content", description);
            option.put("language", "ko");
            option.put("model", "news");
            option.put("tone", "2");
            option.put("summaryCount", "3");

            data.put("document", document);
            data.put("option", option);

            writer.write(data.toJSONString());
            System.out.println(data.toJSONString());
            writer.flush();

            int responseCode = summaryApiConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                String response = readBody(summaryApiConnection.getInputStream());
                result = (String) ((JSONObject) jsonParser.parse(response)).get("summary");
                item.replace("description", result);
            }
            System.out.println("after summary: " + item.get("description"));
        }

        System.out.println(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    // Connect 접속
    private HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    // Respone Body 받기
    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
