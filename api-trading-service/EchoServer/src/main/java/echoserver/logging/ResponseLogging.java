package echoserver.logging;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;

public class ResponseLogging implements ContainerResponseFilter {
private Logger logger;
	
	public ResponseLogging(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		logger.info("Response status: " + responseContext.getStatus() + " for request: " + requestContext.getMethod()
				+ " " + requestContext.getUriInfo().getRequestUri());
		
		if (responseContext.hasEntity()) {
            // Log response entity if present
            logger.info("Response entity: " + responseContext.getEntity().toString());
        }
	}
}
