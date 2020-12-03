package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 *
 * @author Tobin
 */
public class LocationsHandler implements HttpHandler {
    private JSONObject locationsJSON;

    public LocationsHandler(Hashtable<String, String> nameToIDMap) {
        ArrayList<String> locations = Collections.list(nameToIDMap.keys());
        JSONObject jsonBuilder = new JSONObject();
        JSONObject[] p = new JSONObject[locations.size()];

        for (int i = 0; i < p.length; i++) {
            JSONObject locationJSON = new JSONObject();
            locationJSON.put("name", locations.get(i));
            locationJSON.put("id", nameToIDMap.get(locations.get(i)));
            p[i] = locationJSON;
        }

        jsonBuilder.put("locations", p);

        this.locationsJSON = jsonBuilder;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRemoteAddress());

        String reply = locationsJSON.toString();
        exchange.sendResponseHeaders(200, reply.getBytes().length);
        OutputStream response = exchange.getResponseBody();

        response.write(reply.getBytes());
        response.flush();
        response.close();
    }

}
