package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/")
public class Api extends Application {
	
	@Context
    private UriInfo uriInfo;
	
	@Context
	private HttpHeaders httpHeaders;
	
	@Context
	private Request request;
	
	/**
	 * This Endpoint echos a GET Reguest in terms of<br>
	 * * URL<br>
	 * * Path<br>
	 * * Method<br>
	 * * Headers
	 * @return the above request details, formatted as a json string
	 */
	@GET
	@Path("{s:.*}")
	@Produces("application/json")
	public Response echoGet() {
		return Controller.echoNoBody(request, httpHeaders, uriInfo);
	}
	
	/**
	 * This Endpoint echos a POST Reguest in terms of<br>
	 * * URL<br>
	 * * Path<br>
	 * * Method<br>
	 * * Headers<br>
	 * * Body
	 * @param body The request body
	 * @return
	 */
	@POST
	@Path("{s:.*}")
	@Produces("application/json")
	public Response echoPost(String body) {
		return Controller.echoBody(body, request, httpHeaders, uriInfo);
	}
	
	@POST
	@Path("/theOnlyOne")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response theOnlyOne(String body) {
		return Controller.echoBody(body, request, httpHeaders, uriInfo);
	}
	
	/**
	 * This Endpoint echos a PUT Reguest in terms of<br>
	 * * URL<br>
	 * * Path<br>
	 * * Method<br>
	 * * Headers<br>
	 * * Body
	 * @param body The request body
	 * @return
	 */
	@PUT
	@Path("{s:.*}")
	@Produces("application/json")
	public Response echoPut(String body) {
		return Controller.echoBody(body, request, httpHeaders, uriInfo);
	}
	
	/**
	 * This Endpoint echos a PATCH Reguest in terms of<br>
	 * * URL<br>
	 * * Path<br>
	 * * Method<br>
	 * * Headers<br>
	 * * Body
	 * @param body The request body
	 * @return
	 */
	@PATCH
	@Path("{s:.*}")
	@Produces("application/json")
	public Response echoPatch(String body) {
		return Controller.echoBody(body, request, httpHeaders, uriInfo);
	}
	
	/**
	 * This Endpoint echos a DELETE Reguest with a payload in terms of<br>
	 * * URL<br>
	 * * Path<br>
	 * * Method<br>
	 * * Headers<br>
	 * * Body
	 * @param body The request body
	 * @return
	 */	
	@DELETE
	@Path("{s:.*}")	
	@Produces("application/json")
	public Response echoDeleteBody(String body) {	
		return Controller.echoBody(body, request, httpHeaders, uriInfo);
	}
//	@DELETE
//	@Path("{s:.*}")	
//	@Produces("application/json")
//	public Response echoDeleteBody(InputStream bodyStream) {
//		System.out.println("DELETESBY");
//		try {
//			return Controller.echoBody(new String(bodyStream.readAllBytes(), StandardCharsets.UTF_8), request, httpHeaders, uriInfo);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return Controller.echoBody("", request, httpHeaders, uriInfo);
//		}
//	}
	
	/**
	 * This Endpoint echos a DELETE Reguest in terms of<br>
	 * * URL<br>
	 * * Path<br>
	 * * Method<br>
	 * * Headers<br>
	 * @return
	 */
	@DELETE
	@Path("{s:.*}")
	@Produces("application/json")
	public Response echoDelete() {
		return Controller.echoNoBody(request, httpHeaders, uriInfo);
	}
}
