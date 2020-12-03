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
import java.util.Hashtable;
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
        // Create initial list of locations so we can pass into the Map variable on
        // initialization
        ArrayList<Location> allLocations = new ArrayList<Location>();
        // Create a hashtable to keep track of what name corresponds to what ID
        Hashtable<String, String> nameToIDMap = new Hashtable<>();
        // Create a hashtable to keep track of what ID corresponds to what Location
        Hashtable<String, Location> idToLocationMap = new Hashtable<>();
        // Create a hashtable to keep track of what connections each location has
        Hashtable<String, ArrayList<String>> idToConnectionsMap = new Hashtable<>();
        // Scanner variable; will be used to read the CSV file containing all the
        // locations
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("OKTFloor1Locations.csv"));
        // Declaring delimiter to check throughout the CSV file
        scanner.useDelimiter(",");
        // Loop to read CSV file into ArrayList<Location> so we can retrieve all of the
        // locations in the CSV file
        while (scanner.hasNext()) {
            // TODO: Read each individual location in from the CSV file and insert into
            // ArrayList<Location> allLocations
            String lineData[] = scanner.nextLine().split(",");
            try {
                // Check if this location has a name
                if (lineData[1].length() > 0) {
                    nameToIDMap.put(lineData[1], lineData[0]);
                }
                // Retrieve read X-value from CSV, converting from a string to an integer
                int X = (int) Float.parseFloat(lineData[2]);
                // Retrieve read Y-value from CSV, converting from a string to an integer
                int Y = (int) Float.parseFloat(lineData[3]);
                // Retrieve read Z-value from cSV, converting from a string to an integer
                int Z = (int) Float.parseFloat(lineData[4]);
                // Create location object with read values from the CSV file
                Location readLocation = new Location(X, Y, Z, new ArrayList<Location>());
                // Add read location from CSV file into ArrayList of total locations
                allLocations.add(readLocation);
                // Map ID to Location object
                idToLocationMap.put(lineData[0], readLocation);

                ArrayList<String> conns = new ArrayList<>();
                for (int i = 5; i < lineData.length; i++) {
                    if (lineData[i].length() > 0)
                        conns.add(lineData[i]);
                }
                idToConnectionsMap.put(lineData[0], conns);
            } catch (NumberFormatException e) {
                // This skips the format error from the header line in the CSV
            }
        }

        // Config Options for Web Server
        final String IP_ADDRESS = "localhost";
        final int THREAD_COUNT = 3; // The number of threads that the web server can utilize
        int port = 9090; // default port

        boolean serverNotCreated = true;
        HttpServer server = null;
        Random random = new Random();

        while (serverNotCreated) {
            try {
                server = HttpServer.create(new InetSocketAddress(IP_ADDRESS, port), 0);
                serverNotCreated = false;
                System.out.println("Bound server to " + server.getAddress());
            } catch (BindException e) {
                System.out.println("Unable to bind to port " + port + ". Trying again!");
                port = random.nextInt(65565);
            }
        }

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_COUNT);

        server.setExecutor(executor);
        server.createContext("/", new PathHandler("index.html", "text/html"));
        server.createContext("/index.js", new PathHandler("index.js", "text/javascript"));
        server.createContext("/OKTFloor1.svg", new PathHandler("OKTFloor1.svg", "image/svg+xml"));
        server.createContext("/svg-inject.min.js", new PathHandler("svg-inject.min.js", "text/javascript"));
        server.createContext("/locations", new LocationsHandler(nameToIDMap.keys()));
        server.createContext("/navigate", new NavRequestHandler());
        server.createContext("/exit", new ExitHandler(server));

        server.start();

        // Launch Web Page in Browser
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("http://" + server.getAddress()));
        }
    }
}
