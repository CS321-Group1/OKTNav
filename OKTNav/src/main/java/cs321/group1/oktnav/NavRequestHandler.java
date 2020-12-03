package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import org.json.JSONObject;

/**
 *
 * @author Areeb
 */
public class NavRequestHandler implements HttpHandler {
    private Map map;
    public NavRequestHandler(Map map) {
        this.map = map;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String[] params = query.split("&");
        String navFrom = params[0].replace("from=", "");
        String navTo = params[1].replace("to=", "");
        String preference = params[2].replace("pref=", "");
        
        System.out.println(navFrom + " to " + navTo + " with preference of " + preference);
        
        //Path path = new Path(p, 300);
        //String reply = path.getJSON().toString();
        String reply = "test";
        exchange.sendResponseHeaders(200, reply.getBytes().length);
        OutputStream response = exchange.getResponseBody();
        
        response.write(reply.getBytes());
        response.flush();
        response.close();
    }
    
}
