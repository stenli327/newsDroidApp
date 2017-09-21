package com.news.api;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class ObjectUtil {

	private ObjectUtil() {}
	
	public static boolean equals(Object one, Object two) {
		if (one == null && two != null)
			return false;
		if (one != null && !one.equals(two))
			return false;

		return true;
	}
	
	/**
	 * @param str
	 * @return true if the string is empty or null
	 */
	public static boolean isEmptyOrNull(String str) {
		return str == null || str.isEmpty();
	}
	
	/**
	 * 
	 * @param collection
	 * @return true if collection is null or empty
	 */
	public static boolean isEmptyOrNull(Collection<?> collection){
		return collection==null || collection.isEmpty();
	}
	
	/**
	 * @param strings
	 * @return true if all strings are empty or null
	 */
	public static boolean isEmptyOrNull(String ... strings) {
		for (String s : strings) {
			if (!isEmptyOrNull(s))
				return false;
		}
		
		return true;
	}
	
	/**
	 * @param <T> any type
	 * @param value
	 * @param defaultValue
	 * @return value if value is not null otherwise defaultValue
	 */
	public static <T> T ifNull(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * Answers value of defaultValue if value is empty. For String or Collection<?> checks isEmpty() method for other objects acts as ifNull.
	 * 
	 * @param <T> any type
	 * @param value
	 * @param defaultValue
	 * @return value if value is null false or for String and Collection<?> isEmpty is false returns default value
	 */
	public static <T> T ifEmpty(T value, T defaultValue) {
		if (isEmpty(value)) {
			return defaultValue;
		}
		
		return value;
	}
	
	/**
	 * @param value
	 * @return true if value is null and if String or Collection<?> isEmpty is true
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		
		if (value instanceof String) {
			return ((String) value).isEmpty();
		}
		
		if (value instanceof Collection<?>) {
			return ((Collection<?>) value).isEmpty();
		}
		
		if (value instanceof Map<?, ?>) {
			return ((Map<?, ?>) value).isEmpty();
		}
		
		return false;
	}
	
	public static String trimIfNotNull(String string) {
		if (string == null)
			return null;
		
		return string.trim();
	}
	
	public static Integer toInteger(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Integer) {
			return (Integer) value;
		}
		if (value instanceof String) {
			return Integer.parseInt((String) value);
		}
		if (value instanceof BigDecimal) {
			return ((BigDecimal) value).intValue();
		}
		if (value instanceof Boolean) {
			return (Boolean) value ? 0 : 1;
		}
		
		return null;
	}
	
	public static String join(Collection<?> col, String delim) {
	    StringBuilder sb = new StringBuilder();
	    Iterator<?> iter = col.iterator();
	    if (iter.hasNext())
	        sb.append(iter.next().toString());
	    while (iter.hasNext()) {
	        sb.append(delim);
	        sb.append(iter.next().toString());
	    }
	    return sb.toString();
	}
}
