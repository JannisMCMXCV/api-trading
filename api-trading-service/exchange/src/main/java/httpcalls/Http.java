package httpcalls;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import util.MapUtils;

public class Http {
	private static final Logger logger = LoggerFactory.getLogger(Http.class);
	private HttpClient client;

	public Http() {
		client = createClient();
	}

	private HttpClient createClient() {
		return HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).build();
	}

	public HttpResponse<String> get(String url, Map<String, String> params, Map<String, String> headers)
			throws IOException, InterruptedException {
		HttpRequest getRequest = baseRequestBuidlder(url, params, headers).build();

		return send(getRequest);
	}

	public HttpResponse<String> post(String url, Map<String, String> params, Map<String, String> headers, String body)
			throws IOException, InterruptedException {
		HttpRequest postRequest = postRequestBuilder(url, params, headers, body).build();
		return send(postRequest);
	}

	private Builder postRequestBuilder(String url, Map<String, String> params, Map<String, String> headers,
			String body) {

		byte[] bodyBytes = body != null ? body.getBytes(StandardCharsets.UTF_8) : "".getBytes(StandardCharsets.UTF_8);
		completeHeaders(headers, bodyBytes);
		System.out.println(headers);

		Builder requestBuilder = baseRequestBuidlder(url, params, headers);

		if (bodyBytes != null) {
			requestBuilder.POST(BodyPublishers.ofByteArray(bodyBytes));
		} else {
			requestBuilder.POST(BodyPublishers.noBody());
		}

		return requestBuilder;
	}

	private void completeHeaders(Map<String, String> headers, byte[] bodyBytes) {
		String contentTypeHeader = "Content-Type";
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		if (bodyBytes == null) {
			bodyBytes = "".getBytes();
		}
		if (!MapUtils.containsKeyIgnoreCase(headers, contentTypeHeader)) {
			headers.put(contentTypeHeader, isJsonBody(bodyBytes) ? "application/json" : "text/plain");
		}
	}

	private boolean isJsonBody(byte[] bodyBytes) {
		String body = new String(bodyBytes, StandardCharsets.UTF_8);
		if (bodyBytes == null) {
			return false;
		}
		try {
			JsonParser.parseString(body);
			return true;
		} catch (JsonSyntaxException e) {
			return false;
		}
	}

	public HttpResponse<String> put(String url, Map<String, String> params, Map<String, String> headers, String body)
			throws IOException, InterruptedException {
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

	public HttpResponse<String> patch(String url, Map<String, String> params, Map<String, String> headers, String body)
			throws IOException, InterruptedException {
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

	public HttpResponse<String> delete(String url, Map<String, String> params, Map<String, String> headers)
			throws IOException, InterruptedException {
		HttpRequest deleteRequest = baseRequestBuidlder(url, params, headers).DELETE().build();
		return send(deleteRequest);
	}

	public HttpResponse<String> delete(String url, Map<String, String> params, Map<String, String> headers, String body)
			throws IOException, InterruptedException {
		HttpRequest deleteRequest = deleteRequestBuilder(url, params, headers, body).build();
		return send(deleteRequest);
	}

	private Builder baseRequestBuidlder(String url, Map<String, String> params, Map<String, String> headers) {
		url = buildUrl(url, params);
		Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.timeout(Duration.ofSeconds(30));

		if (headers != null) {
			headers.forEach(requestBuilder::header);
		}
		return requestBuilder;
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
		if (params == null || params.isEmpty()) {
	        return url;
	    }
	    StringBuilder urlBuilder = new StringBuilder(url);

	    if (!url.contains("?")) {
	        urlBuilder.append('?');
	    } else if (!url.endsWith("&") && !url.endsWith("?")) {
	        urlBuilder.append('&');
	    }

	    params.entrySet().stream()
	          .map(entry -> entry.getKey() + "=" + entry.getValue() + "&")
	          .forEach(urlBuilder::append);
	    removeTrailingSymbol(urlBuilder);
	    logger.info("Resulting URL: {}", urlBuilder.toString());
	    return urlBuilder.toString();
	}

	private void removeTrailingSymbol(StringBuilder urlBuilder) {
		urlBuilder.deleteCharAt(urlBuilder.length() - 1);
	}

	private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
		logger.info("Sending request to: {}", request.uri());
	    logger.info("Request method: {}", request.method());
	    
	    request.headers().map().forEach((k, v) -> logger.info(k + ": " + String.join(", ", v)));
	    logger.info("Request Headers: " + request.headers().map());
	    
	    if (request.bodyPublisher().isPresent()) {
	    	System.out.println(request.bodyPublisher().get().getClass());
	    }
	    try {
	    	HttpResponse<String> response = client.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
	    	logger.info("Response received with status code: " + response.statusCode());
	    	return response;
	    } catch (IOException | InterruptedException e) {
	    	logger.error("Could not send request. " + e.getMessage());
	    	e.printStackTrace();
	    	throw e;
	    }
	}
}
