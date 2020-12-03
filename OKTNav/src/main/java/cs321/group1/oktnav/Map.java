package cs321.group1.oktnav;

import java.util.ArrayList;
import java.util.Hashtable;
import org.json.JSONObject;
/**
 * Class implementation of the Map class
 * This class contains the list of locations that exist in the OKT building
 * @author Drew
 */
public class Map {
    private Hashtable<String, String> nameToIDMap;
    private Hashtable<String, Location> idToLocationMap;
    // Member variable; stores all VerticalTransitions declared
    private ArrayList<VerticalTransition> verticalTransitions;
    // Member variable; stores the path from starting point to end point
    private Path path;
    /**
     * Constructor for the Map class
     * @param locations - list of locations that exist in the OKT building
     */    
    public Map(Hashtable<String, String> nameToIDMap, Hashtable<String, Location> idToLocationMap, ArrayList<VerticalTransition> verticalTransitions) {
        this.nameToIDMap = nameToIDMap;
        this.idToLocationMap = idToLocationMap;
        this.verticalTransitions = verticalTransitions;
    }
     /**
      * Function that sets the path from the starting point to the end point, selected by the user
      * @param path - the path from the starting point to the end point
      */
    public void setPath(Path path){
        // Setting the parameter path as the object's path
        this.path = path;
    }
    
    /**
     * Resets the cost fields in all Locations so they are ready for the pathfinder.
     */
    public void resetAllLocations(){
        for (Location l: idToLocationMap.values()){
            l.reset();
        }
    }
    
    /**
     * Function that returns the calculated path from the starting point to the end point
     * @return the path from the starting point to the end point
     */
    public Path getPath(){
        // Returning the object's path where necessary
        return path;
    }
    
    public Location getLocationByID(String id) {
        return idToLocationMap.get(id);
    }
    
    public Hashtable<String, String> getNameToIDMap() {
        return nameToIDMap;
    }
    
    public ArrayList<VerticalTransition> getVerticalTransitions(){
        return verticalTransitions;
    }
}