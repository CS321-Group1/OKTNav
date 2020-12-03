package cs321.group1.oktnav;

import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Areeb
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        // Create initial list of locations so we can pass into the Map variable on initialization
        ArrayList<Location> allLocations = new ArrayList<Location>();
        // Scanner variable; will be used to read the CSV file containing all the locations
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("OKTFloor1Locations.csv"));
        // Declaring delimiter to check throughout the CSV file
        scanner.useDelimiter(",");
        // Loop to read CSV file into ArrayList<Location> so we can retrieve all of the locations in the CSV file
        while(scanner.hasNext()){
            // TODO: Read each individual location in from the CSV file and insert into ArrayList<Location> allLocations
            String lineData[] = scanner.nextLine().split(",");
            // Retrieve read X-value from CSV, converting from a string to an integer
            int X = (int) Float.parseFloat(lineData[2]);
            // Retrieve read Y-value from CSV, converting from a string to an integer
            int Y = (int) Float.parseFloat(lineData[3]);
            // Retrieve read Z-value from cSV, converting from a string to an integer
            int Z = (int) Float.parseFloat(lineData[4]);
            // Create location object with read values from the CSV file
            Location readLocation = new Location(X, Y, Z, new ArrayList<Location>());
            // Add read location from  CSV file into ArrayList of total locations
            allLocations.add(readLocation);
        }

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
