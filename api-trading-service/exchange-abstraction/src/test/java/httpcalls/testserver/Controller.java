package httpcalls.testserver;

import com.google.gson.Gson;
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
		response.addProperty("url",  uriInfo.getAbsolutePath().toString());
		response.addProperty("path", uriInfo.getPath().toString());
		response.addProperty("method", request.getMethod());
		response.add("headers", new Gson().toJsonTree(httpHeaders.getRequestHeaders()));
		
		if(body != null) {
			try {
				response.add("body", JsonParser.parseString(body));
	        } catch (JsonSyntaxException e) {
	        	response.addProperty("body", body);
	        }
		}
		return gson.toJson(response);
	}
	
}
