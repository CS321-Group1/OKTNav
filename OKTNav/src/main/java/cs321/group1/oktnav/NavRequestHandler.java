package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author Areeb
 */
public class NavRequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String[] params = query.split("&");
        String navFrom = params[0].replace("from=", "");
        String navTo = params[1].replace("to=", "");
        String preference = params[2].replace("pref=", "");
        
        System.out.println(navFrom + " to " + navTo + " with preference of " + preference);
        
        Location a = new Location(0, 0, 0, new ArrayList<Location>());
        Location b = new Location(5, 0, 0, new ArrayList<Location>());
        Location c = new Location(10, 0, 0, new ArrayList<Location>());
        Location d = new Location(10, 5, 0, new ArrayList<Location>());        
        
        ArrayList<Location> p = new ArrayList<>();
        p.add(a);
        p.add(b);
        p.add(c);
        p.add(d);
        
        Path path = new Path(p, 300);
        
        String reply = path.getJSON().toString();
        exchange.sendResponseHeaders(200, reply.getBytes().length);
        OutputStream response = exchange.getResponseBody();
        
        response.write(reply.getBytes());
        response.flush();
        response.close();
    }
    
}
