package cs321.group1.oktnav;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Class implementation of the Navigation class;
 * This will calculate the path from the user's start point to the endpoint
 * @author Drew and Annaleise
 */
public class Navigator {
    
    private Map map;
    
     /**
     * Constructor for Navigator class;
     * @param m - Object of type Map that contains an ArrayList of data type Location of path from start point to end point
     */
    public Navigator(Map m){
        map = m;
    }
    
     /** Function that returns a calculated route from the start point to the end point, selected by the user
     * @param source - starting location selected by the user
     * @param goal; - ending location selected by the user
     * @param navigationFlag - flag set to determine whether or not the user needs to take elevators, stairs, or no preference. (-1 = no preference, 0 = stairs, 1 = elevators)
     * @return 
     */   
    public Path findRoute(Location source, Location goal, int navigationFlag){
        
        map.resetAllLocations();
            
        //If the source and destination are on the same floor, return the basic A* path
        if(source.getZ() == goal.getZ()){
            return calculatePath(source, goal);
        }
        
        
        //otherwise, we must consider all the routes through different vertical Transitions
        
        //best path holds the best path we have found so far
        Path bestPath = new Path(new ArrayList<Location>(), Double.MAX_VALUE);
        
        //for each vertical transition on the map...
        for(VerticalTransition vt : map.getVerticalTransitions() ){
            //if it fits the vertical transition preference...
            if(navigationFlag == -1 || (navigationFlag == 0 && vt.isElevator() == false) || (navigationFlag == 1 && vt.isElevator() == true)){
               //calculate the path from the source to the vert transition
               Path pathA = calculatePath(source, vt);
               
               //and the path from the vert transition to the goal
               Path pathB = calculatePath(vt, goal);
               
               //if the sum of these path lengths is better than any of our previous paths...
               if(pathA.getLength() + pathB.getLength() < bestPath.getLength()){
                   ArrayList<Location> joinedPath = new ArrayList<Location>();
                   joinedPath.addAll(pathA.getPath());
                   joinedPath.addAll(pathB.getPath());
                   
                   //set the best path to be the sum of those two paths 
                   bestPath = new Path (joinedPath, pathA.getLength() + pathB.getLength());
               }
            }
        }
            
        return bestPath;
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
     * Function that returns the calculated path from the start point to the end point - either user selected locations or verticalTransitions
     * @param source - starting location
     * @param goal; - ending location
     * @return - path from the start to end locations
     */
    public Path calculatePath(Location source, Location goal){
        source.setG(0); //the accumulated cost to get to the first node is 0
        source.setH(calcH(source, goal)); //h cost calculated as straight line distance with calcH
        source.setF(source.getH()); //since g_cost is 0 for the source, f_cost is just h_cost
        
        ArrayList<Location> path = new ArrayList<>();
        
        //Create priority queue of 'frontier' locations in the graph
        PriorityQueue<Location> frontier = new PriorityQueue<>(new Comparator<Location>() {
            @Override
            public int compare(Location a, Location b){
                return Double.compare (a.getF(), b.getF());
            }
        });
        
        //Add our first source node to the frontier
        frontier.add(source);
        
        //initialize step tracker
        int numberOfSteps = 0;
        
        //while there are still locations on the frontier...
        while(!frontier.isEmpty()) {
            numberOfSteps++;
            
            //get the Location with the minumum estimated distance from the goal
            Location current = frontier.poll();
            
            System.out.println("Current location: " + current.getX() + ", " 
                                                    + current.getY() + ", " 
                                                    + current.getZ());
            //if we have found our goal Location...
            if(current.equals(goal)){
                System.out.println("We found the goal at: " + current.getX() + ", " 
                                                        + current.getY() + ", " 
                                                        + current.getZ());
                break;
            }
            
            //check all the neighbors
            for(Location neighbor: current.getConnections()){
                System.out.println("\tConsidering: " + neighbor.getX() + "," 
                                                    + neighbor.getY() + ',' 
                                                    + neighbor.getZ());
                
                double cost = current.calculateDistance(neighbor);
                
                neighbor.setH(calcH(neighbor, goal));
                
                //if we go to this neighbor location...
                //our new g cost will be our current g cost plus the distance to the neighbor
                double new_g_cost = current.getG() + cost;      
                //and our new f cost will be that new g cost plus the neighbor's h cost
                double new_f_cost = neighbor.getH() + new_g_cost;
                
                //if this neigbor is the best option...
                if(new_f_cost < neighbor.getF()){
                    neighbor.setParent(current);
                    neighbor.setG( new_g_cost);
                    neighbor.setF( new_f_cost);
                    
                    if(frontier.contains(neighbor)){
                        frontier.remove(neighbor);
                    }
                    frontier.add(neighbor);
                }
            }
        }
        
        System.out.println("Number Of Steps: " + numberOfSteps);
        
        Location x = goal;
        double length = 0;
        while(x != null && !x.equals(source)){
            path.add(0, x);
            length += x.calculateDistance(x.getParent());
            x = x.getParent();
        }
        path.add(0, source);
      
        
        return new Path(path, length);
    }
}