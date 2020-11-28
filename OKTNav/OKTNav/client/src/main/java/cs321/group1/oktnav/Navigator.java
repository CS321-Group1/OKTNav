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
     * Calculates the heuristic value for use by the A* navigation.
     * @param current the current location
     * @param goal the current goal
     * @return the straight line distance between the current location and the goal
     */
    private double calcH(Location current, Location goal){
        return Math.sqrt( Math.pow(current.getY() - goal.getY(), 2) + 
                Math.pow(current.getX() - goal.getX(), 2));
    }
    
    /**
     * Function that returns the calculated path from the start point to the end point, selected by the user
     * @param source - starting location selected by the user
     * @param goal; - ending location selected by the user
     * @param navigationFlag - flag set to determine whether or not the user needs to take elevators, stairs, or no preference
     * @return 
     */
    public ArrayList<Location> calculatePath(Location source, Location goal, int navigationFlag){
        source.setGCost(0); //the accumulated cost to get to the first node is 0
        source.setHCost(calcH(source, goal));
        
        
        
        return new ArrayList<Location>(calculatedPath);
    }
}
