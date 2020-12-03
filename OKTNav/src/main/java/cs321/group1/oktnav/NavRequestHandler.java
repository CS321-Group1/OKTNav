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
    private Navigator navigator;
    private Hashtable<String, Integer> preferenceToFlagMap;
    public NavRequestHandler(Map map) {
        this.map = map;
        navigator = new Navigator(map);
        preferenceToFlagMap = new Hashtable<>();
        preferenceToFlagMap.put("n/a", -1);
        preferenceToFlagMap.put("stairs", 0);
        preferenceToFlagMap.put("elevator", 1);
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String[] params = query.split("&");
        String navFrom = params[0].replace("from=", "");
        String navTo = params[1].replace("to=", "");
        String preference = params[2].replace("pref=", "");
        
        System.out.println(navFrom + " to " + navTo + " with preference of " + preference);
        
        
        Location from = map.getLocationByID(navFrom);
        Location to = map.getLocationByID(navTo);
        int navigationFlag = preferenceToFlagMap.get(preference);
        Path path = navigator.findRoute(from, to, navigationFlag);
        String reply = path.getJSON().toString();
        
        exchange.sendResponseHeaders(200, reply.getBytes().length);
        OutputStream response = exchange.getResponseBody();
        
        response.write(reply.getBytes());
        response.flush();
        response.close();
    }
    
}
