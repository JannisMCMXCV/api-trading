package httpcalls;

import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;

public class Http {

	public HttpResponse<String> get(String url, Map<String, String> params, Map<String, String> headers) throws IOException, InterruptedException {
		HttpRequest getRequest = baseRequestBuidlder(url, params, headers)
				.GET()
				.build();
		
		return send(getRequest);
	}
	
	public HttpResponse<String> post(String url, Map<String, String> params, Map<String, String> headers, String body) throws IOException, InterruptedException {
		HttpRequest postRequest = postRequestBuilder(url, params, headers, body)
				.build();
		return send(postRequest);
	}
	
	private Builder postRequestBuilder(String url, Map<String, String> params, Map<String, String> headers,
			String body) {
		
		Builder requestBuilder = baseRequestBuidlder(url, params, headers);
		
		if (body != null) {
			requestBuilder.POST(BodyPublishers.ofString(body));
		} else {
			requestBuilder.POST(BodyPublishers.noBody());
		}

		return requestBuilder;
	}
	
	public HttpResponse<String> put(String url, Map<String, String> params, Map<String, String> headers, String body) throws IOException, InterruptedException {
		HttpRequest putRequest = putRequestBuilder(url, params, headers, body).build();
		return send(putRequest);
	}
	
	private Builder putRequestBuilder(String url, Map<String, String> params, Map<String, String> headers,
			String body) {
		Builder requestBuilder = baseRequestBuidlder(url, params, headers);
		
		if (body != null) {
			requestBuilder.PUT(BodyPublishers.ofString(body));
		} else {
			requestBuilder.PUT(BodyPublishers.noBody());
		}
		return requestBuilder;
	}
	
	public HttpResponse<String> patch(String url, Map<String, String> params, Map<String, String> headers, String body) throws IOException, InterruptedException {
		HttpRequest patchRequest = patchRequestBuilder(url, params, headers, body).build();
		return send(patchRequest);
	}
	
	private Builder patchRequestBuilder(String url, Map<String, String> params, Map<String, String> headers,
			String body) {
		Builder requestBuilder = baseRequestBuidlder(url, params, headers);
		
		if (body != null) {
			requestBuilder.method("PATCH", BodyPublishers.ofString(body));
		} else {
			requestBuilder.method("PATCH", BodyPublishers.noBody());
		}
		return requestBuilder;
	}
	
	public HttpResponse<String> delete(String url, Map<String, String> params, Map<String, String> headers, String body) throws IOException, InterruptedException {
		HttpRequest deleteRequest = deleteRequestBuilder(url, params, headers, body).build();
		return send(deleteRequest);
	}
	
	private Builder deleteRequestBuilder(String url, Map<String, String> params, Map<String, String> headers,
			String body) {
		Builder requestBuilder = baseRequestBuidlder(url, params, headers);
		
		if (body != null) {
			requestBuilder.method("DELETE", BodyPublishers.ofString(body));
		} else {
			requestBuilder.DELETE();
		}
		return requestBuilder;
	}
	
	

	private String buildUrl(String url, Map<String, String> params) {
		if(params == null || params.isEmpty()) {
			return url;
		}	
		StringBuilder urlBuilder = new StringBuilder(url);
		
		if(!url.endsWith("?")){
			urlBuilder.append('?');
		}
		
		params.entrySet().stream()
			.map(entry -> entry.getKey() + "=" + entry.getValue() + "&")
			.forEach(urlBuilder::append);
		urlBuilder.deleteCharAt(urlBuilder.length() - 1);
		return urlBuilder.toString();
	}
	
	private Builder baseRequestBuidlder(String url, Map<String, String> params, Map<String, String> headers) {
		url = buildUrl(url, params);
		Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.timeout(Duration.ofSeconds(10));
		
		if (headers != null) {
			headers.forEach(requestBuilder::header);
		}
		return requestBuilder;
	}
	

	private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
		try (HttpClient client = HttpClient.newBuilder()
				.connectTimeout(Duration.ofSeconds(5))
				.build()) {
			
			return client.send(request, BodyHandlers.ofString());
		}
	}
}
