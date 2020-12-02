package cs321.group1.oktnav;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.html.boot.BrowserBuilder;

public final class Main {
    private Main() {
    }

    public static void main(String... args) throws Exception {
        // Create initial list of locations so we can pass into the Map variable on initialization
        ArrayList<Location> allLocations = new ArrayList<Location>();
        try {
            // Scanner variable; will be used to read the CSV file containing all the locations
            Scanner scanner = new Scanner(new File(System.getProperty("user.dir") + "/src/main/java/cs321/group1/oktnav/data/OKTFloor1Locations.csv"));
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
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        System.setProperty("prism.order", "sw");
        BrowserBuilder.newBrowser().
            loadPage("pages/index.html").
            loadClass(Main.class).
            invoke("onPageLoad", args).
            showAndWait();
        System.exit(0);
    }

    /**
     * Called when the page is ready.
     */
    public static void onPageLoad() throws Exception {
        // don't put "common" initialization stuff here, other platforms (iOS, Android, Bck2Brwsr) may not call this method. They rather call DataModel.onPageLoad
        DataModel.onPageLoad();
    }

}
