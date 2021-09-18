package kr.pe.rigizer.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonUtil {
	public static JSONObject getJsonObject(String json) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		
		try {
			jsonObject = (JSONObject) jsonParser.parse(json);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
}
