package kr.pe.rigizer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class YoutubeService {
	private final String apiKey = "(GOOGLE_YOUTUBE_API_KEY)";
	
	public Map<String, Object> getYoutubeSearchData(String word) {
		String json = getJsonData(word);
		
		Map<String, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private String getJsonData(String word) {
		String text = null;
		
		try {
			text = URLEncoder.encode(word, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("WORD ENCODING FAILTURE: ", e);
		}
		
		StringBuilder url = new StringBuilder();
		String apiUrl = "https://www.googleapis.com/youtube/v3/search";
		
		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);			// 구글 API키 (유튜브 데이터)
		params.put("part", "snippet");		// 상세정보 출력 (snippet)
		params.put("maxResults", "10");		// 최대 결과수
		params.put("q", text);				// 검색어
		
		url.append(apiUrl);
		boolean firstParam = true;
		for (String key: params.keySet()) {
			if (firstParam == true) {
				url.append("?");
				firstParam = false;
			} else {
				url.append("&");
			}
			
			String value = params.get(key);
			url.append(key);
			url.append("=");
			url.append(value);
		}
		
		apiUrl = url.toString();
		
		return get(apiUrl);
	}
	
	private String get(String apiUrl) {
		HttpURLConnection con = connect(apiUrl);
		
		try {
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				return readBody(con.getInputStream());
			} else {
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API REQUEST AND RESPONSE FAILTURE: ", e);
		} finally {
			con.disconnect();
		}
	}
	
	private HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL IS WRONG [" + apiUrl + "]: ", e);
		} catch (IOException e) {
			throw new RuntimeException("CONNECT FAILTURE [" + apiUrl + "]: ", e);
		}
	}
	
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
			throw new RuntimeException("API RESPONSER READ FAILTURE: ", e);
		}
	}
}
