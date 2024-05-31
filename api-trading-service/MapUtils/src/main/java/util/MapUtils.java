package util;

import java.util.Map;
import java.util.Set;

public class MapUtils {
	
	/**
	 * Determines whether a string key exists in a map, ignoring the case.
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param key
	 * @return
	 */
	public static  <K, V> boolean containsKeyIgnoreCase(Map<K, V> map, String key) {
		Set<K> keySet = map.keySet();
		if (!keySetContainsStrings(keySet)) {
			return map.containsKey(key);
		}
		
		for(K mapKey : keySet) {
			if(((String) mapKey).equalsIgnoreCase(key)) {
				return true;
			}
		}		
		return false;
		
	}

	private static <K> boolean keySetContainsStrings(Set<K> keySet) {
		for(K key : keySet) {
			return key instanceof String;
		}
		return false;
	}
	
	/**
	 * TODO
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param entry
	 * @return
	 */
	public static <K, V> boolean containsEntryIgnoreKeyCase(Map<K, V> map, Map.Entry<K, V> entry) {
		Set<K> keySet = map.keySet();
		if (!keySetContainsStrings(keySet)) {
			return map.entrySet().contains(entry);
		}
		
		for (Map.Entry<K, V> mapEntry : map.entrySet()) {
			if(entryKeyEqualsOtherEntryKeyIgnoreCase(mapEntry, entry)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * TODO
	 * @param <K>
	 * @param <V>
	 * @param one
	 * @param other
	 * @return
	 */
	public static <K, V> boolean entryKeyEqualsOtherEntryKeyIgnoreCase(Map.Entry<K, V> one, Map.Entry<K, V> other) {
		if(!(one.getKey() instanceof String)) {
			return false;
		}
		
		String oneLowercaseKey = ((String) one.getKey()).toLowerCase();
		String otherLowercaseKey = ((String) other.getKey()).toLowerCase();
		
		return (oneLowercaseKey.equals(otherLowercaseKey) && one.getValue().equals(other.getValue()));

	}
	
}
