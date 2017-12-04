package com.fable.insightview.platform.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 此类描述的是：json 工具类
 * 
 * @author chengdawei
 */

public class JsonUtil {
	private static final Logger logger = LogManager.getLogger();
	public final static ObjectMapper mapper = CommonUtil
			.getMapperInstance(false);

	/**
	 * 
	 * 此方法描述的是：根据key取得相应的值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return String
	 */
	public static String getString(Map<String, Object> map, String key) {
		try {
			return (String) map.get(key);
		} catch (Exception e) {
			return JsonUtil.toString(map.get(key));
		}
	}

	/**
	 * 
	 * 此方法描述的是：取得list
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return List<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getList(Map<String, Object> map,
			String key) {
		try {
			return (List<Map<String, Object>>) map.get(key);
		} catch (Exception e) {
			try {
				return JsonUtil.toList(map.get(key));
			} catch (Exception e2) {
				return null;
			}
		}
	}

	/**
	 * 
	 * 此方法描述的是：取得list
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(Map<String, Object> map, String key) {
		try {
			return (Map<String, Object>) map.get(key);
		} catch (Exception e) {
			return JsonUtil.toBean(map.get(key), Map.class);
		}

	}

	/**
	 * 
	 * 此方法描述的是：根据key取值int
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return int
	 */
	public static int getInt(Map<String, Object> map, String key,
			int defaultValue) {
		try {
			return (Integer) map.get(key);
		} catch (Exception e) {
			try {
				return Integer.parseInt(JsonUtil.toString(map.get(key)));
			} catch (Exception e2) {
				return defaultValue;
			}
		}
	}

	/**
	 * 
	 * 此方法描述的是：根据key取BigDecial
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(Map<String, Object> map, String key,
			BigDecimal defaultValue) {
		return new BigDecimal(getDouble(map, key, defaultValue.doubleValue()));
	}

	/**
	 * 
	 * 此方法描述的是：根据key取BigDecial
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return BigDecimal
	 */
	public static BigDecimal getBigDecimal(Map<String, Object> map, String key) {
		return new BigDecimal(getDouble(map, key));
	}

	/**
	 * 
	 * 此方法描述的是：根据key取值int
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return int
	 */
	public static int getInt(Map<String, Object> map, String key) {
		return JsonUtil.getInt(map, key, 0);
	}

	/**
	 * 
	 * 此方法描述的是：根据key取得boolean值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return boolean
	 */
	public static boolean getBoolean(Map<String, Object> map, String key,
			boolean defaultValue) {
		try {
			return (Boolean) map.get(key);
		} catch (Exception e) {
			try {
				return Boolean.parseBoolean(JsonUtil.toString(map.get(key)));
			} catch (Exception e2) {
				return defaultValue;
			}
		}
	}

	/**
	 * 
	 * 此方法描述的是：根据key取得boolean值,默认为false
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return boolean
	 */
	public static boolean getBoolean(Map<String, Object> map, String key) {
		return getBoolean(map, key, false);
	}

	/**
	 * 
	 * 此方法描述的是：向obj数组中加新元素,
	 * 
	 * @param list
	 *            list
	 * @param obj
	 *            增加的元素
	 */
	@SuppressWarnings("unchecked")
	public static void add(List<HashMap<String, Object>> list, Object obj) {
		list.add(JsonUtil.toBean(list, HashMap.class));
	}

	/**
	 * 
	 * 此方法描述的是：根据key取得double值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return double
	 */
	public static double getDouble(Map<String, Object> map, String key,
			double defaultValue) {
		try {
			return (Double) map.get(key);
		} catch (Exception e) {
			try {
				return Double.parseDouble(JsonUtil.toString(map.get(key)));
			} catch (Exception e2) {
				return defaultValue;
			}
		}

	}

	/**
	 * 
	 * 此方法描述的是：根据key取得double值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return double
	 */
	public static double getDouble(Map<String, Object> map, String key) {
		return getDouble(map, key, 0D);
	}

	/**
	 * 
	 * 此方法描述的是：根据key取得double值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return long
	 */
	public static long getLong(Map<String, Object> map, String key,
			long defaultValue) {
		try {
			return (Long) map.get(key);
		} catch (Exception e) {
			try {
				return Long.parseLong(JsonUtil.toString(map.get(key)));
			} catch (Exception e2) {
				return defaultValue;
			}
		}
	}

	/**
	 * 
	 * 此方法描述的是：根据key取得double值
	 * 
	 * @param map
	 *            欲取值的map
	 * @param key
	 *            key
	 * @return long
	 */
	public static long getLong(Map<String, Object> map, String key) {
		return getLong(map, key, 0L);
	}

	/**
	 * 
	 * 此方法描述的是：将Object转化为Json格式字符串
	 * 
	 * @param obj
	 *            欲转换的对象
	 * @return String
	 */
	public static String toString(Object obj) {
		StringWriter writer = new StringWriter();
		JsonGenerator gen;
		String json = null;
		try {
			gen = new JsonFactory().createJsonGenerator(writer);
			mapper.writeValue(gen, obj);
			gen.close();
			json = writer.toString();
			writer.close();
		} catch (IOException e) {
			logger.error("Json转换错误");
		}
		return json;
	}

	/**
	 * 
	 * 此方法描述的是：将Object转化为Json格式字符串
	 * 
	 * @param obj
	 *            欲转换的对象
	 * @param dateFormat
	 *            日期format
	 * @return String
	 */
	// public static String toString(Object obj,DateFormat dateFormat) {
	// if (obj instanceof String) {
	// return (String) obj;
	// } else if (null == obj) {
	// return null;
	// }
	// try {
	// JsonFormatter.setDateFormat(dateFormat);
	// return JsonFormatter.toJsonString(obj);
	// } catch (JsonGenerationException e) {
	// LOG.error("JsonGenerationException error", e);
	// } catch (JsonMappingException e) {
	// LOG.error("JsonMappingException error", e);
	// } catch (IOException e) {
	// LOG.error("IOException error", e);
	// }
	// return null;
	// }

	/**
	 * 
	 * 此方法描述的是：将传入的对象转换成指定的对象
	 * 
	 * @param <T>
	 *            模板类
	 * @param cls
	 *            与转化的类
	 * @param obj
	 *            被转换的对象
	 * @return T
	 */
	public static <T> T toBean(Object obj, Class<T> cls) {
		if (null == obj) {
			return null;
		}
		try {
			/*obj类型为String时，JsonUtil.toString(obj)会给obj添加多余的"",例如"obj"*/
			if (obj instanceof String) {
				mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			    return	mapper.readValue(obj + "", cls);
			}
			return mapper.readValue(JsonUtil.toString(obj), cls);
		} catch (Exception e) {
			logger.error("Json转换错误");
		}
		return null;
	}

	public static Object jsonToBean(String json, Class<?> cls) {
		Object vo = null;
		try {
			vo = mapper.readValue(json, cls);
		} catch (JsonParseException e) {
			logger.error("Json转换错误");
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			logger.error("Json转换错误");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Json转换错误");
		}
		return vo;
	}

	/**
	 * 
	 * 此方法描述的是：字符串转换为List<map>
	 * 
	 * @param obj
	 *            与转换的对象
	 * @return List<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> toList(Object obj) {
		List<Map<String, Object>> lists = new LinkedList<Map<String, Object>>();
		List<Object> list = JsonUtil.toBean(obj, List.class);
		if (null != list) {
			for (Object object : list) {
				Map<String, Object> map = JsonUtil
						.toBean(object, HashMap.class);
				if (null != map) {
					lists.add(map);
				}
			}
		}
		return lists;
	}
	
	/**
	 * 
	 * 此方法描述的是：字符串转换为List<map>
	 * 
	 * @param obj
	 *            与转换的对象
	 * @return List<Map<String, String>>
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> toListString(Object obj) {
		List<Map<String, String>> lists = new LinkedList<Map<String, String>>();
		List<Object> list = JsonUtil.toBean(obj, List.class);
		if (null != list) {
			for (Object object : list) {
				Map<String, String> map = JsonUtil
						.toBean(object, HashMap.class);
				if (null != map) {
					lists.add(map);
				}
			}
		}
		return lists;
	}

	/**
	 * 
	 * 此方法描述的是：字符串转换为List
	 * 
	 * @param <T>
	 *            模板类
	 * @param cls
	 *            与转化的类
	 * @param obj
	 *            被转换的对象
	 * @return T
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(Object obj, Class<T> cls) {
		List<T> lists = new LinkedList<T>();
		List<Object> list = JsonUtil.toBean(obj, List.class);
		if (null != list) {
			for (Object object : list) {
				T t = JsonUtil.toBean(object, cls);
				if (null != t) {
					lists.add(t);
				}
			}
		}
		return lists;
	}

	public static Map json2Map(String json) {
		try {
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			mapper.configure(
					DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
					true);
			return mapper.readValue(json, Map.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Json转换错误");
		}
		return null;
	}

	public static String map2Json(Map<String, Object> map) {
		try {
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Json转换错误");
		}
		return null;
	}

	public static String listMap2Json(List<Map<String, String>> map) {
		try {
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Json转换错误");
		}
		return null;
	}

	public static void main(String[] a) {
		String s = "{\"linkmax\":\"30M\",\"CiId\":\"2\",\"bagh\":\"20KG\",\"koujing\":\"3.2CM\",\"color\":\"\u767d\u8272\",\"chakou\":\"24\",\"editing\":false}";
		String s1 = "{\"aaa\":\"123\",\"data\":{\"bbb\":\"456\"}}";
		Map m = json2Map(s1);
		Map m2 = (Map) m.get("data");
		System.out.println(m2);
	}

	/**
	 * 将map转换成Javabean
	 * 
	 * @param javabean
	 *            javaBean
	 * @param data
	 *            map数据
	 * @throws ClassNotFoundException
	 */
	public static Object toJavaBean(String javabean, Map<String, String> data)
			throws ClassNotFoundException {
		Class clazz = Class.forName(javabean);
		Field[] fAll = clazz.getDeclaredFields();
		return javabean;
	}

	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
		
			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

}
