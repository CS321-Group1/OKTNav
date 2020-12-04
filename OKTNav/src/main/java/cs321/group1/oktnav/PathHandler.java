package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A class that serves files in the resource directory to the GUI.
 * @author Areeb and Tobin
 */
public class PathHandler implements HttpHandler {
    private final String path;
    private final String contentType;

    /**
     * Constructor for the PathHandler class.
     * @param path path to the file from the resource directory.
     * @param contentType HTML identifier for the content type.
     */
    public PathHandler(String path, String contentType) {
        this.path = path;
        this.contentType = contentType;
    }

    /**
     * Handles the exchange. Replies with 
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Loads the resources as a stream
        InputStream page = ClassLoader.getSystemResourceAsStream(path);
        
        // Prepares the response to the GUI
        exchange.getResponseHeaders().set("Content-Type", contentType + ";");
        exchange.sendResponseHeaders(200, page.available());
        OutputStream response = exchange.getResponseBody();
        
        // Writes from the resource stream to the response stream
        response.write(page.readAllBytes());
        response.flush();
        response.close();
    }
}
