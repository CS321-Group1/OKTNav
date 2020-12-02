package cs321.group1.oktnav;

import java.util.ArrayList;
/**
 * Class implementation of the Map class
 * This class contains the list of locations that exist in the OKT building
 * @author Drew
 */
public class Map {
    // Member variable; stores all locations declared
    private ArrayList<Location> allLocations;
    // Member variable; stores the path from starting point to end point
    private ArrayList<Location> path;
    /**
     * Constructor for the Map class
     * @param locations - list of locations that exist in the OKT building
     */
    public Map(ArrayList<Location> locations){
        allLocations = new ArrayList<Location>(locations);
    }
     /**
      * Function that sets the path from the starting point to the end point, selected by the user
      * @param path - the path from the starting point to the end point
      */
    public void setPath(ArrayList<Location> path){
        // Setting the parameter path as the object's path
        this.path = new ArrayList<Location>(path);
    }
    
    /**
     * Function that returns the calculated path from the starting point to the end point
     * @return the path from the starting point to the end point
     */
    public ArrayList<Location> getPath(){
        // Returning the object's path where necessary
        return new ArrayList<Location>(path);
    }
    
    public ArrayList<VerticalTransition> getVerticalTransitions(){
        return new ArrayList<VerticalTransition>();
    }
}