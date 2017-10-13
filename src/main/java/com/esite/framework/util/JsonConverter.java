package com.esite.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

public class JsonConverter<T extends Serializable> {

	public static String convert(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		JsonGenerator gen;
		try {
			gen = new JsonFactory().createJsonGenerator(writer);
			mapper.writeValue(gen, object);
			gen.close();
			writer.close();
			String json = writer.toString();
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static <T> T convert(String json,Class<T> clas) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, clas);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T convert(String json,Class<?> clas,Class<?>... elementClasses) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(clas, elementClasses);   
		try {
			return mapper.readValue(json, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T convert(InputStream is,Class<T> clas) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(is, clas);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
