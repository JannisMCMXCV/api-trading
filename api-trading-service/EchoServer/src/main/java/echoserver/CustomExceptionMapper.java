package echoserver;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class CustomExceptionMapper implements ExceptionMapper<Throwable> {
	@Override
    public Response toResponse(Throwable exception) {
        // Log exception here
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("An unexpected error occurred")
                       .build();
    }
}
