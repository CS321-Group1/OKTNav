package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A class that shuts down the application upon receiving an exit message.
 * @author Areeb
 */
public class ExitHandler implements HttpHandler {
    // Reference to the server instance running the app.
    private HttpServer server;
    
    /**
     * Constructor for the ExitHandler.
     * @param server the server instance running the app.
     */
    public ExitHandler(HttpServer server) {
        // Store reference to server
        this.server = server;
    }    

    /**
     * Handles the exchange. Replies to the exchange with a confirmation before
     * shutting down the application.
     * @param exchange the HttpExchange between the app and front end.
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String reply = "I am Inevitable";
        
        // Prepares response to the GUI
        exchange.sendResponseHeaders(200, reply.getBytes().length);
        OutputStream response = exchange.getResponseBody();
        
        // Writes from the resource stream to the response stream
        response.write(reply.getBytes());
        response.flush();
        response.close();
        
        // Shutdown Sequence
        server.stop(0);
        System.out.println("End Game");
        System.exit(0);
    }
}
