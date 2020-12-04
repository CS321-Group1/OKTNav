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
public class IndexHandler implements HttpHandler {

    /**
     * 
     * @param exchange
     * @throws IOException 
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRemoteAddress());
        
        InputStream indexPage = ClassLoader.getSystemResourceAsStream("index.html");
        
        exchange.sendResponseHeaders(200, indexPage.available());
        OutputStream response = exchange.getResponseBody();
        
        response.write(indexPage.readAllBytes());
        response.flush();
        response.close();
    }
    
}
