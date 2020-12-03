package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

/**
 *
 * @author Areeb
 */
public class ExitHandler implements HttpHandler {
    private HttpServer server;
    public ExitHandler(HttpServer server) {
        this.server = server;
    }    

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        server.stop(0);
        System.out.println("End Game");
        System.exit(0);
    }
}
