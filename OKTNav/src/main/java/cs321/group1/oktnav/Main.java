package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author Areeb
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        // Config Options for Web Server
        final String IP_ADDRESS = "localhost";
        final int THREAD_COUNT = 3; // The number of threads that the web server can utilize
        int port = 9090; // default port
        
        boolean serverNotCreated = true;
        HttpServer server = null;
        Random random = new Random();     
        
        while(serverNotCreated)
        {
            try {
                server = HttpServer.create(new InetSocketAddress(IP_ADDRESS, port), 0);
                serverNotCreated = false;
                System.out.println("Bound server to " + server.getAddress());
            } catch (BindException e) {
                System.out.println("Unable to bind to port " + port + ". Trying again!");
                port = random.nextInt(65565);
            }
        }       
        
        ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(THREAD_COUNT);
        
        server.setExecutor(executor);
        server.createContext("/", new PathHandler("index.html"));
        server.createContext("/index.js", new PathHandler("index.js"));
        server.createContext("/navigate", new NavRequestHandler());
        server.createContext("/exit", new ExitHandler(server));
        
        server.start();
        
        // Launch Web Page in Browser
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("http://" + server.getAddress()));
        }
    }
}
