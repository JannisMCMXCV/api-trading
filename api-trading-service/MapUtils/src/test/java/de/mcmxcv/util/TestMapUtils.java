package de.mcmxcv.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class TestMapUtils {
	
	@Test
	public void containsKeyIgnoreCase() {
		Map<String, Object> testMap = new HashMap<>();
		testMap.put("TeSt", "testValue");
		assertTrue(MapUtils.containsKeyIgnoreCase(testMap, "test"));	
		assertFalse(MapUtils.containsKeyIgnoreCase(testMap, "tset"));
	}
	
	@Test
	public void containsEntryIgnoreKeyCase() {
		Map<String, Object> testMap = new HashMap<>();
		Map<String, Object> compareMap = new HashMap<>();
		Map<String, Object> compareFalseKeyMap = new HashMap<>();
		Map<String, Object> compareFalseValueMap = new HashMap<>();
		
		testMap.put("TeSt", "testValue");
		compareMap.put("test", "testValue");
		compareFalseKeyMap.put("false", "testValue");
		compareFalseValueMap.put("test", "falseValue");
		
		Map.Entry<String, Object> testEntry = compareMap.entrySet().iterator().next();
		Map.Entry<String, Object> falseKeyEntry = compareFalseKeyMap.entrySet().iterator().next();
		Map.Entry<String, Object> falseValueEntry = compareFalseValueMap.entrySet().iterator().next();
		
		System.out.println(falseValueEntry);
		
		assertTrue(MapUtils.containsEntryIgnoreKeyCase(testMap, testEntry));	
		assertFalse(MapUtils.containsEntryIgnoreKeyCase(testMap, falseKeyEntry));	
		assertFalse(MapUtils.containsEntryIgnoreKeyCase(testMap, falseValueEntry));	
	}
	
	@Test
	public void entryKeyEqualsOtherEntryKeyIgnoreCase() {
		Map<String, Object> helperMap = new HashMap<>();
		Map<String, Object> helperCompareMap = new HashMap<>();
		Map<String, Object> helperCmpareFalseMap = new HashMap<>();
		
		helperMap.put("TeSt", "testValue");
		helperCompareMap.put("test", "testValue");
		helperCmpareFalseMap.put("tset", "testValue");
		
		Map.Entry<String, Object> testEntry = helperMap.entrySet().iterator().next();
		Map.Entry<String, Object> compareEntry = helperCompareMap.entrySet().iterator().next();
		Map.Entry<String, Object> compareFalseEntry = helperCmpareFalseMap.entrySet().iterator().next();
		
		assertTrue(MapUtils.entryKeyEqualsOtherEntryKeyIgnoreCase(testEntry, compareEntry));
		assertFalse(MapUtils.entryKeyEqualsOtherEntryKeyIgnoreCase(testEntry, compareFalseEntry));
	}
}
