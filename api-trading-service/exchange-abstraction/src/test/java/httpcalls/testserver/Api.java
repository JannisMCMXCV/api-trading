package httpcalls.testserver;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;

@Path("/")
public class Api {
	
	@Context
    private UriInfo uriInfo;
	
	@Context
	private HttpHeaders httpHeaders;
	
	@Context
	private Request request;
	
	@GET
	@Path("{s:.*}")
	@Produces("application/json")
	public String health() { 
		return "{\"status\": \"healthy\"}";
	}
	
	@GET
	@Path("get-echo")
	@Produces("application/json")
	public String echoGet() {
		return Controller.echoNoBody(request, httpHeaders, uriInfo);
	}
	
	@POST
	@Path("post-echo")
	@Produces("application/json")
	public String echoPost(String body) {
		return Controller.echoBody(body, request, httpHeaders, uriInfo);
	}
	
	@PUT
	@Path("put-echo")
	@Produces("application/json")
	public String echoPut(String body) {
		return Controller.echoBody(body, request, httpHeaders, uriInfo);
	}
	
	@PATCH
	@Path("patch-echo")
	@Produces("application/json")
	public String echoPatch(String body) {
		return Controller.echoBody(body, request, httpHeaders, uriInfo);
	}
	
	@DELETE
	@Path("delete-echo")
	@Produces("application/json")
	public String echoDeleteBody(String body) {
		return Controller.echoBody(body, request, httpHeaders, uriInfo);
	}
	
	@DELETE
	@Path("delete-echo")
	@Produces("application/json")
	public String echoDelete() {
		return Controller.echoNoBody(request, httpHeaders, uriInfo);
	}
}
