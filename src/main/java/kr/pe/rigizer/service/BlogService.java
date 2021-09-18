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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.pe.rigizer.util.JsonUtil;

@Service
public class BlogService {
	private final String clientId = "(CLIENT_ID)";
	private final String clientSecret = "(CLIENT_SECRET)";
	
	public Map<String, Object> getBlogSearchData(String word) {
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
		
		String apiUrl = "https://openapi.naver.com/v1/search/blog?query=" + text;
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiUrl,requestHeaders);
		
		return responseBody;
	}
	
	private String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header: requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}
			
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
