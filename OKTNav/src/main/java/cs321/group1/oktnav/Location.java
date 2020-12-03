package cs321.group1.oktnav;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 * A class that holds information about locations on the OKT Map.
 * @author Areeb
 */
public class Location {
    private final int x;
    private final int y;
    private final int z;
    
    //These are used for each location during the A* Pathfinder
    private double h_cost = Integer.MAX_VALUE;
    private double g_cost = Integer.MAX_VALUE;
    private double f_cost = Integer.MAX_VALUE;
    private Location parent;
    
    private ArrayList<Location> connections;
    
    /**
     * Constructs a Location object with the given information.
     * @param x the x coordinate on the map.
     * @param y the y coordinate on the map.
     * @param z the z coordinate on the map (arbitrary representation of each floor).
     * @param connections an ArrayList\<Location\> of other locations this Location is connected to.
     */
    public Location(int x, int y, int z, ArrayList<Location> connections) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.connections = new ArrayList<Location>(connections);
    }
    
    /**
     * Calculates the distance to another connected location.
     * @param other the other location to consider.
     * @return the distance between the two locations.
     */
    public double calculateDistance(Location other) throws IllegalArgumentException {
        if (connections.contains(other)){
            return Math.sqrt(
                    Math.pow(this.x-other.x, 2) +
                    Math.pow(this.y-other.y, 2) +
                    Math.pow(this.z-other.z, 2)
            );
        } else {
            throw new IllegalArgumentException("The provided Location other is not connected to this particular node.");
        }
    }
    /**
     * Returns the X coordinate of this location.
     * @return the X coordinate of this location.
     */
    public int getX() { return x; }
    /**
     * Returns the Y coordinate of this location.
     * @return the Y coordinate of this location.
     */
    public int getY() { return y; }
    /**
     * Returns the Z coordinate of this location.
     * @return the Z coordinate of this location.
     */
    public int getZ() { return z; }
    
    /**
     * Returns a copy of the list of connections to this location.
     * @return a copy of the list of connections.
     */
    public ArrayList<Location> getConnections() {
        return new ArrayList<Location>(connections);
    }
    
    /**
     * Adds a new location to the list of neighbors
     * @param x the location to add
     * @return true for successful addition of the location to the list
     */
    public boolean addConnection(Location x){
        return connections.add(x);
    }
    /**
     * Returns the h_cost for this location.
     * @return the h_cost for this location.
     */
    public double getH() {return h_cost; }
    
     /**
     * Returns the g_cost for this location.
     * @return the g_cost for this location.
     */
    public double getG() {return g_cost; }
    
     /**
     * Returns the f_cost for this location.
     * @return the f_cost for this location.
     */
    public double getF() {return f_cost; }
    
     /**
     * Returns the parent for this location.
     * @return the parent for this location.
     */
    public Location getParent() {return parent; }
    
    
     /**
     * Sets the h_cost for this location.
     */
    public void setH(double h) {h_cost = h; }
    
      /**
     * Sets the g_cost for this location.
     */
    public void setG(double g) {g_cost = g; }
    
    /**
     * Sets the f_cost for this location.
     */
    public void setF(double f) {f_cost = f; }
     
    /**
     * Sets the parent for this location.
     */
    public void setParent(Location p) {parent = p; }
    
    public JSONObject getJSON() {
        JSONObject jsonBuilder = new JSONObject();
        
        jsonBuilder.put("x", x);
        jsonBuilder.put("y", y);
        jsonBuilder.put("z", z);
        
        return jsonBuilder;
    }    
}
