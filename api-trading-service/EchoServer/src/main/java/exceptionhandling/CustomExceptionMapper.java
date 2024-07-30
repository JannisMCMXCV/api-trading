package exceptionhandling;


import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class CustomExceptionMapper implements ExceptionMapper<Throwable> {
	private static final Logger logger = Logger.getLogger(CustomExceptionMapper.class.getName());

	@Override
    public Response toResponse(Throwable exception) {
		logger.log(Level.SEVERE, "Unhandled exception:"
				+ "\nMessage: " + exception.getMessage()
				+ "\nLocalized Message: " + exception.getLocalizedMessage()
				/*+ "\nCuase Message:" + exception.getCause().getMessage()
				+ "\nCause Localized Message" + exception.getCause().getLocalizedMessage()*/, exception);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("An unexpected error occurred")
                       .build();
    }
}
