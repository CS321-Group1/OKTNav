package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
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

        JSONObject replyBuilder = new JSONObject();        
        replyBuilder.put("test", 2013);
        
        String reply = replyBuilder.toString();
        
        exchange.sendResponseHeaders(200, reply.getBytes().length);
        OutputStream response = exchange.getResponseBody();
        
        response.write(reply.getBytes());
        response.flush();
        response.close();
    }
    
}
