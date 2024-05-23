package httpcalls;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import httpcalls.testserver.Server;

public class TestHttp {
	private Http http = new Http();
	
	private final static String baseUrl = "http://localhost:8080";
	private final static String testPath = "/test";
	private final static String testBody = "{\"thisIsA\": \"test Body\"}";
	private static Map<String, String> testParams = new HashMap<>();
	private static Map<String, String> testHeaders = new HashMap<>();
	
	@BeforeAll
	public static void setupAll() {
		testParams.put("thisIsA", "testParam");
		testHeaders.put("accept", "application/json");
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

//	@Test
	@SuppressWarnings("unchecked")
	public void post() throws IOException, InterruptedException {
		HttpResponse<String> response = http.post(baseUrl + testPath, testParams, testHeaders, testBody);
		
		Map<String, List<String>> responseMap = responseToMap(response);
	
		System.out.println(responseMap);
		assertEquals(testPath, responseMap.get("path"));
		assertTrue(((Map<String, String>) responseMap.get("params")).entrySet().containsAll(testParams.entrySet()));
		assertExpectedHeaders(testHeaders, responseMap);
	}

//	@Test
	public void put() throws IOException, InterruptedException {

	}
	
//	@Test
	public void patch() throws IOException, InterruptedException {

	}
	
//	@Test
	public void delete() throws IOException, InterruptedException {

	}
	
	@SuppressWarnings("unchecked")
	private Map<String, List<String>> responseToMap(HttpResponse<String> response) {
		Gson gson = new Gson();
		return gson.fromJson(response.body(), Map.class);
	}
	
	@SuppressWarnings("unchecked")
	private void assertExpectedHeaders(Map<String, String> expectedHeaders, Map<String, List<String>> response) {
		Map<String, List<String>> responseHeaders = ((Map<String, List<String>>) response.get("headers"));
		valueArrayListifyExpectedHeaders(expectedHeaders).entrySet()
		.forEach(entry -> assertTrue(responseHeaders.entrySet().contains(entry)));		
	}
	
	private Map<String, List<String>> valueArrayListifyExpectedHeaders(Map<String, String> expectedHeaders) {
		return expectedHeaders.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(), entry -> valueListify(entry.getValue())));
	}
	
	private List<String> valueListify(String... str) {
		return Arrays.asList(str);
		
	}
	
}
