package echoserver.logging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.message.internal.ReaderWriter;

public class RequestLogging implements ContainerRequestFilter {
	private Logger logger;
	
	public RequestLogging(Logger logger) {
		this.logger = logger;
	}
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
		logger.info("Received request: " + requestContext.getMethod() + " " + requestContext.getUriInfo().getRequestUri());
		logRequestHeaders(requestContext);
        if (requestContext.hasEntity()) {
        	logRequestBody(requestContext);
        }
    }

	private void logRequestHeaders(ContainerRequestContext requestContext) {
		logger.info("Request Headers: " + requestContext.getHeaders());
		
	}

	private void logRequestBody(ContainerRequestContext requestContext) throws IOException {
		InputStream inputStream = requestContext.getEntityStream();
        try {
            String body = ReaderWriter.readFromAsString(inputStream, new MediaType(MediaType.CHARSET_PARAMETER, null, "UTF-8"));
            logger.info("Request Body: " + body);
            
            // Reset the entity stream, so Jersey can process the request as usual
            requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read request body", e);
            throw e;
        }
	}
}