package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import org.json.JSONObject;

/**
 * A class that serves navigation requests to the GUI.
 * @author Areeb
 */
public class NavRequestHandler implements HttpHandler {
    private Map map;
    private Navigator navigator;
    private Hashtable<String, Integer> preferenceToFlagMap;
    
    /**
     * Constructs a NavRequest handler given a map.
     * @param map 
     */
    public NavRequestHandler(Map map) {
        // Stores map and creates Navigator instance
        this.map = map;
        navigator = new Navigator(map);
        
        // Creates a Hashtable to choose between different navigation types
        preferenceToFlagMap = new Hashtable<>();
        preferenceToFlagMap.put("n/a", -1);
        preferenceToFlagMap.put("stairs", 0);
        preferenceToFlagMap.put("elevator", 1);
    }
    
    /**
     * Handles the exchange. Parses the navigation request and runs it through
     * the navigator.
     * @param exchange the HttpExchange between the app and front end.
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Retrieve the navigation query
        String query = exchange.getRequestURI().getQuery();
        
        // Split the query into parameters and read them into variables
        String[] params = query.split("&");
        String navFrom = params[0].replace("from=", "");
        String navTo = params[1].replace("to=", "");
        String preference = params[2].replace("pref=", "");
        
        // Retrieve the Location objects from the Map
        Location from = map.getLocationByID(navFrom);
        Location to = map.getLocationByID(navTo);
        int navigationFlag = preferenceToFlagMap.get(preference);
        
        // Run the request through the navigator
        Path path;
        try {
            path = navigator.findRoute(from, to, navigationFlag);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return;
        }
        // If successful, send the path to the GUI
        String reply = path.getJSON().toString();
        
        // Prepares the response to the GUI
        exchange.sendResponseHeaders(200, reply.getBytes().length);
        OutputStream response = exchange.getResponseBody();
        
        // Writes the response to the GUI
        response.write(reply.getBytes());
        response.flush();
        response.close();
    }
    
}
