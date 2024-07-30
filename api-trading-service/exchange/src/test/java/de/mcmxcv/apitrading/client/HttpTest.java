package de.mcmxcv.apitrading.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;

import de.mcmxcv.util.MapUtils;
import echoserver.Server;

public class HttpTest {
	private Http http = new Http();

	private final static String baseUrl = "http://localhost:8080";
	private final static String testPath = "/test";
	private final static String testBody = "{\n" + "   \"name\": \"TEST\",\n" + "   \"data\": {\n"
			+ "      \"test\": \"thank you for letting me test\"\n" + "   }\n" + "}";
	private static Map<String, String> testParams = new HashMap<>();
	private static Map<String, String> testHeaders = new HashMap<>();

	@BeforeAll
	public static void setupAll() {
		testParams.put("thisIsA", "testParam");
		testHeaders.put("Content-Type", "application/json");
		testHeaders.put("X-Custom-Header", "custom");
		Server.startServer();
	}

	@AfterAll
	public static void tearDown() {
		Server.stopServer();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void get() throws IOException, InterruptedException {
		HttpResponse<String> response = http.get(baseUrl + testPath, testParams, testHeaders);

		Map<String, List<String>> responseMap = responseToMap(response);

		assertEquals(testPath, responseMap.get("path"));
		assertTrue(((Map<String, String>) responseMap.get("params")).entrySet().containsAll(testParams.entrySet()));
		assertExpectedHeaders(testHeaders, responseMap);
	}

	@Test
	public void postOnline() throws IOException, InterruptedException {
		HttpResponse<String> response = http.post("https://api.restful-api.dev/objects", testParams, testHeaders,
				testBody);
		System.out.println(response);
		response.statusCode();
		assertEquals(200, response.statusCode());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void post() throws IOException, InterruptedException {
		HttpResponse<String> response = http.post(baseUrl + testPath, testParams, testHeaders, testBody);
		System.out.println(response);
		Map<String, List<String>> responseMap = responseToMap(response);

		System.out.println(responseMap);
		assertEquals(testPath, responseMap.get("path"));
		assertTrue(((Map<String, String>) responseMap.get("params")).entrySet().containsAll(testParams.entrySet()));
		assertExpectedHeaders(testHeaders, responseMap);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void put() throws IOException, InterruptedException {
		HttpResponse<String> response = http.put(baseUrl + testPath, testParams, testHeaders, testBody);
		Map<String, List<String>> responseMap = responseToMap(response);

		System.out.println(responseMap);
		assertEquals(testPath, responseMap.get("path"));
		assertTrue(((Map<String, String>) responseMap.get("params")).entrySet().containsAll(testParams.entrySet()));
		assertExpectedHeaders(testHeaders, responseMap);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void patch() throws IOException, InterruptedException {
		HttpResponse<String> response = http.patch(baseUrl + testPath, testParams, testHeaders, testBody);
		Map<String, List<String>> responseMap = responseToMap(response);

		System.out.println(responseMap);
		assertEquals(testPath, responseMap.get("path"));
		assertTrue(((Map<String, String>) responseMap.get("params")).entrySet().containsAll(testParams.entrySet()));
		assertExpectedHeaders(testHeaders, responseMap);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void delete() throws IOException, InterruptedException {
		HttpResponse<String> response = http.delete(baseUrl + testPath, testParams, testHeaders);

		Map<String, List<String>> responseMap = responseToMap(response);

		assertEquals(testPath, responseMap.get("path"));
		assertTrue(((Map<String, String>) responseMap.get("params")).entrySet().containsAll(testParams.entrySet()));
		assertExpectedHeaders(testHeaders, responseMap);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deleteWithBdy() throws IOException, InterruptedException {
		HttpResponse<String> response = http.delete(baseUrl + testPath, testParams, testHeaders, testBody);
		Map<String, List<String>> responseMap = responseToMap(response);
		
		assertNotNull(responseMap);
		assertEquals(testPath, responseMap.get("path"));
		assertTrue(((Map<String, String>) responseMap.get("params")).entrySet().containsAll(testParams.entrySet()));
		assertExpectedHeaders(testHeaders, responseMap);
	}

	@SuppressWarnings("unchecked")
	private Map<String, List<String>> responseToMap(HttpResponse<String> response) {
		if (response.body() == null) {
			return null;
		}
		Gson gson = new Gson();
		return gson.fromJson(response.body(), Map.class);
	}

	@SuppressWarnings("unchecked")
	private void assertExpectedHeaders(Map<String, String> expectedHeaders, Map<String, List<String>> response) {
		Map<String, List<String>> responseHeaders = ((Map<String, List<String>>) response.get("headers"));
		valueArrayListifyExpectedHeaders(expectedHeaders).entrySet()
				.forEach(entry -> assertTrue(MapUtils.containsEntryIgnoreKeyCase(responseHeaders, entry)));
	}

	private Map<String, List<String>> valueArrayListifyExpectedHeaders(Map<String, String> expectedHeaders) {
		return expectedHeaders.entrySet().stream()
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> valueListify(entry.getValue())));
	}

	private List<String> valueListify(String... str) {
		return Arrays.asList(str);

	}
}
