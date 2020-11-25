package cs321.group1.oktnav;

import java.util.ArrayList;
/**
 * Class implementation of the Navigation class;
 * This will calculate the path from the user's start point to the endpoint
 * @author Drew and Annaleise
 */
public class Navigator {
    // Member variable; holds the calculated path found using the A* algorithm
    private ArrayList<Location> calculatedPath;
    /**
     * Constructor for Navigator class;
     * @param map - Object of type Map that contains an ArrayList of data type Location of path from start point to end point
     */
    public Navigator(Map map){
    
    }
    
    /**
     * Function that returns the calculated path from the start point to the end point, selected by the user
     * @param from - starting location selected by the user
     * @param to - ending location selected by the user
     * @param navigationFlag - flag set to determine whether or not the user needs to take elevators, stairs, or no preference
     * @return 
     */
    public ArrayList<Location> calculatePath(Location from, Location to, int navigationFlag){
        return new ArrayList<Location>(calculatedPath);
    }
}
