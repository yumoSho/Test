package com.glanway.jty.utils.logistics.util;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**      
 * <p>名称: json解析类</p>
 * <p>说明: 暂时只在快递100中使用，其他项目如果需要使用请不要在这里修改</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 * 
 * @author：Sun.Fan
 * @date：2016年5月10日上午10:12:58   
 * @version: 1.0 
 */
public class JacksonHelper {
	private static ObjectMapper toJSONMapper = new ObjectMapper();
	private static ObjectMapper fromJSONMapper = new ObjectMapper();
	static {
		fromJSONMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		fromJSONMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}

	public static String toJSON(Object obj) {
		ObjectMapper mapper = toJSONMapper;
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, obj);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return writer.toString();
	}

	public static void toJSON(Object obj, OutputStream stream, String charset) {
		ObjectMapper mapper = toJSONMapper;
		try {
			OutputStreamWriter writer = new OutputStreamWriter(stream, charset);
			mapper.writeValue(writer, obj);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromJSON(String json, Class<T> clazz) {
		ObjectMapper mapper = fromJSONMapper;
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromJSON(InputStream json, Class<T> clazz) {
		ObjectMapper mapper = fromJSONMapper;
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> boolean toJSONList(List<T> list) {
		String jsonVal = null;
		try {
			jsonVal = toJSONMapper.writeValueAsString(list);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return jsonVal == null ? false : true;
	}

	@SuppressWarnings("deprecation")
	public static <T> List<T> fromJSONList(String jsonVal, Class<?> clazz) {

		List<T> list = null;
		try {
			list = fromJSONMapper.readValue(jsonVal, TypeFactory.collectionType(ArrayList.class, clazz));
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}
