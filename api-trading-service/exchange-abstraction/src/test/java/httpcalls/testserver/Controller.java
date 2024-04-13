package httpcalls.testserver;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;

public class Controller {

	public static String echoBody(String body, Request request, HttpHeaders httpHeaders, UriInfo uriInfo) {
		System.out.println("Body");
		return request.getMethod();
	}

	public static String echoNoBody(Request request, HttpHeaders httpHeaders, UriInfo uriInfo) {
		System.out.println("NoBody");

		String uri = uriInfo.getPath();
		MultivaluedMap<String, String> gedöns = httpHeaders.getRequestHeaders();
		gedöns.forEach((dings, bums) -> System.out.println(dings + ": "+ bums));
		return request.getMethod() + "\n" + gedöns;
	}
	
}
