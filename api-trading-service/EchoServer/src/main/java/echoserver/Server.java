package echoserver;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http2.Http2AddOn;
import org.glassfish.grizzly.http2.Http2Configuration;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.grizzly2.httpserver.internal.LocalizationMessages;
import org.glassfish.jersey.server.ResourceConfig;

import echoserver.logging.RequestLogging;
import echoserver.logging.ResponseLogging;
import exceptionhandling.CustomExceptionMapper;
import jakarta.ws.rs.ProcessingException;

import java.util.logging.Logger;

public class Server {
	private static final int port = 8080;

	private static final Logger logger = Logger.getLogger(Server.class.getName());

	static HttpServer server;

	public static void main(String... args) {
		startServer();
	}

	public static void startServer() {
		String baseUri = "http://" + NetworkListener.DEFAULT_NETWORK_HOST + ":" + port;
		
		ResourceConfig resourceConfig = new ResourceConfig(Api.class);
		resourceConfig.register(CustomExceptionMapper.class);
		resourceConfig.register(new RequestLogging(logger));
		resourceConfig.register(new ResponseLogging(logger));
		
		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), resourceConfig, false);
		NetworkListener listener = server.getListener("grizzly");
		Http2Configuration configuration = Http2Configuration.builder().build();
		Http2AddOn http2Addon = new Http2AddOn(configuration);
		listener.registerAddOn(http2Addon);
		server.getServerConfiguration().setAllowPayloadForUndefinedHttpMethods(true);
		System.out.println(listener);

		try {
			server.start();
		} catch (IOException e) {
			server.shutdownNow();
			throw new ProcessingException(LocalizationMessages.FAILED_TO_START_SERVER(e.getMessage()), e);
		}
	}

	public static void stopServer() {
		if (server != null && server.isStarted()) {
			server.shutdownNow();
		}
	}
}
