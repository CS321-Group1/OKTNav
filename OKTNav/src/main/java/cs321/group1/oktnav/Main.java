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
        ArrayList<Location> allLocations = new ArrayList<>();
        ArrayList<VerticalTransition> verticalTransitions = new ArrayList<>();
        // Create a hashtable to keep track of what name corresponds to what ID
        Hashtable<String, String> nameToIDMap = new Hashtable<>();
        // Create hashtables to keep track of what ID corresponds to what Location and
        // vice verse
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
                String ID = lineData[0];
                String NAME = lineData[1];
                if (NAME.length() > 0 && !NAME.equals("NAME")) {
                    nameToIDMap.put(NAME, ID);
                }               
                
                // Retrieve read X-value from CSV, converting from a string to an integer
                int X = (int) Float.parseFloat(lineData[2]);
                // Retrieve read Y-value from CSV, converting from a string to an integer
                int Y = (int) Float.parseFloat(lineData[3]);
                // Retrieve read Z-value from cSV, converting from a string to an integer
                int Z = (int) Float.parseFloat(lineData[4]);
                String type = lineData[5];
                // Create location object with read values from the CSV file
                Location readLocation;
                if (type.equals("R")) {
                    readLocation = new Room(ID, NAME, X, Y, Z, new ArrayList<Location>());
                } else if (type.equals("VT/S")) {
                    readLocation = new VerticalTransition(ID, false, X, Y, Z, new ArrayList<Location>());
                    verticalTransitions.add((VerticalTransition) readLocation);
                } else if (type.equals("VT/E")) {
                    readLocation = new VerticalTransition(ID, true, X, Y, Z, new ArrayList<Location>());
                    verticalTransitions.add((VerticalTransition) readLocation);
                } else if (type.equals("D")) {
                    continue;
                } else {
                    readLocation = new Location(ID, X, Y, Z, new ArrayList<Location>());
                }
                // Add read location from CSV file into ArrayList of total locations
                allLocations.add(readLocation);
                // Map ID to Location object
                idToLocationMap.put(ID, readLocation);
                System.out.println(idToLocationMap.get(ID).getJSON());
                ArrayList<String> conns = new ArrayList<>();
                for (int i = 6; i < lineData.length; i++) {
                    if (lineData[i].length() > 0)
                        conns.add(lineData[i]);
                }
                idToConnectionsMap.put(lineData[0], conns);
            } catch (NumberFormatException e) {
                // This skips the format error from the header line in the CSV
                e.printStackTrace();
            }
        }

        for (int i = 0; i < allLocations.size(); i++) {
            Location location = allLocations.get(i);
            ArrayList<String> conns = idToConnectionsMap.get(location.getID());
            for (String conn : conns) {
                Location other = idToLocationMap.get(conn);
                location.addConnection(other);
                try {                
                    other.addConnection(location);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        Map map = new Map(nameToIDMap, idToLocationMap, verticalTransitions);
        /*Navigator nav = new Navigator(map);
        int success = 0;
        int failure = 0;
        String output = "";
        for (String name: nameToIDMap.keySet())
        {
            for (String name_other: nameToIDMap.keySet())
            {
                Location a = map.getLocationByID(nameToIDMap.get(name));
                Location b = map.getLocationByID(nameToIDMap.get(name_other));
                try {
                    nav.findRoute(a, b, -1);
                    success += 1;
                } catch (Exception e) {
                    output += "Failure: " + name + " to " + name_other + " " + e.getMessage() + "\n";
                    failure += 1;
                }
            }
        }
        System.out.println(output);
        System.out.println("Successes: " + success + "\nFailures: " + failure);*/
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
        server.createContext("/locations", new LocationsHandler(nameToIDMap));
        server.createContext("/navigate", new NavRequestHandler(map));
        server.createContext("/exit", new ExitHandler(server));

        server.start();

        // Launch Web Page in Browser
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("http://" + server.getAddress()));
        }
    }
}
