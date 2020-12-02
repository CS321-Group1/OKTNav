package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Areeb
 */
public class PathHandler implements HttpHandler {
    private final String path;
    
    public PathHandler(String path) {
        this.path = path;
    }
  
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRemoteAddress());
        
        InputStream page = ClassLoader.getSystemResourceAsStream(path);
        
        exchange.sendResponseHeaders(200, page.available());
        OutputStream response = exchange.getResponseBody();
        
        response.write(page.readAllBytes());
        response.flush();
        response.close();
    }
    
}