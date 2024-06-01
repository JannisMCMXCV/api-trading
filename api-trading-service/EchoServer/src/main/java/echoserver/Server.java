package echoserver;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Server {
	private static final int port = 8080;
	
	static HttpServer server; 
	public static void main (String... args) {
		startServer();
	}
	
	public static void startServer() { 
		String baseUri = "http://localhost:" + port + "/";
		ResourceConfig resourceConfig = new ResourceConfig(Api.class);
		resourceConfig.register(CustomExceptionMapper.class);
		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), resourceConfig);	
	}
	
	public static void stopServer() {
		if (server != null && server.isStarted()) {
			server.shutdownNow();
		}
	}
}
