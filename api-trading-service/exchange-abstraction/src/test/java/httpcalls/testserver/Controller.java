package httpcalls.testserver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;

public class Controller {

	public static String echoBody(String body, Request request, HttpHeaders httpHeaders, UriInfo uriInfo) {
		return createAnswer(body, request, httpHeaders, uriInfo);
	}

	public static String echoNoBody(Request request, HttpHeaders httpHeaders, UriInfo uriInfo) {
		return createAnswer(null, request, httpHeaders, uriInfo);
	}
	
	private static String createAnswer(String body, Request request, HttpHeaders httpHeaders, UriInfo uriInfo) {
		JsonObject response = new JsonObject();
		Gson gson = new Gson();
		response.addProperty("url",  uriInfo.getRequestUri().toASCIIString());
		response.addProperty("path", getPath(uriInfo.getRequestUri()));
		response.add("params", getParams(uriInfo.getRequestUri()));
		response.addProperty("method", request.getMethod());
		response.add("headers", new Gson().toJsonTree(httpHeaders.getRequestHeaders()));
		response.addProperty("client-host", uriInfo.getRequestUri().getHost());
		
		if(body != null) {
			try {
				response.add("body", JsonParser.parseString(body));
	        } catch (JsonSyntaxException e) {
	        	response.addProperty("body", body);
	        }
		}
		return gson.toJson(response);
	}

	private static JsonElement getParams(URI requestUri) {
		URL url;
		try {
			url = requestUri.toURL();
			String query = url.getQuery();
			if (query == null) {
				return null;
			}
			Map<String, String> queryParams = new HashMap<>();
			String[] keyValuePairs = query.split("&");
			
			for(String pair : keyValuePairs) {
				String[] pairArray = pair.split("=");
				queryParams.put(pairArray[0], pairArray.length > 1 ? pairArray[1] : "");
			}	
			return new Gson().toJsonTree(queryParams);
		} catch (MalformedURLException e) {
			// should not happen :D
			throw new RuntimeException("Could not convert from URI to URL");
		}
		
	}

	private static String getPath(URI requestUri) {
		return requestUri.getPath();
	}
	
}
