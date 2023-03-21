package com.kakaobank.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.registerModule(new JavaTimeModule());
	}
	
	public static <T> T jsonToObject(String json, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	public static String objectToJson(Object actualResponse) {
		try {
			return OBJECT_MAPPER.writeValueAsString(actualResponse);
		} catch (Exception e) {
			return null;
		}
	}
}
