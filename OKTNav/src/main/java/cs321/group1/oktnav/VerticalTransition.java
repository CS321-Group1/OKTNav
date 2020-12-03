package cs321.group1.oktnav;

import java.util.ArrayList;

/**
 * A class that holds information about elevators or stairs on the OKT Map.
 * @author Areeb
 */
public class VerticalTransition extends Location {
    private final int ELEVATOR_DISTANCE = 10;
    private final int STAIRCASE_DISTANCE = 20;
    
    private final boolean isElevator;    
    
    /**
     * Constructs a VerticalTransition object with the given information.
     * @param isElevator whether the transition is an elevator or a staircase.
     * @param x the x coordinate on the map.
     * @param y the y coordinate on the map.
     * @param z the z coordinate on the map.
     * @param connections an ArrayList\<Location\> of other locations this Location is connected to.
     */
    public VerticalTransition(String id, boolean isElevator, int x, int y, int z, ArrayList<Location> connections) {
        super(id, x, y, z, connections);
        this.isElevator = isElevator;
    }
    
    /**
     * Calculates the distance to another connected location.
     * @param other the other location to consider.
     * @return the distance between the two locations.
     */
    @Override
    public double calculateDistance(Location other) throws IllegalArgumentException{
        if (other instanceof VerticalTransition && this.getConnections().contains(other)) {
            if (this.getZ() != other.getZ()) {
                return (isElevator) ? ELEVATOR_DISTANCE : STAIRCASE_DISTANCE;
            } else {
                return super.calculateDistance(other);
            }            
        } else {
            return super.calculateDistance(other);
        }
    }
    
    /**
     * Getter for isElevator boolean.
     * @return true if the vertical transition is an elevator 
     */
    public boolean isElevator(){
        return isElevator;
    }
}