
package cs321.group1.oktnav;
import java.util.ArrayList;

/**
 *
 * @author Annaleise
 * 
 * The path class is just a basic struct-like class to hold a location path and it's length
 */
public class Path {
    private ArrayList<Location> path;
    private double length;
    
    Path( ArrayList<Location> p, double l ){
        path = p;
        length = l;
    }
    
    /**
     * Returns the location path.
     * @return the path
     */
    ArrayList<Location> getPath(){
        return path;
    }
    
    /**
     * Returns the length of the path.
     * @return path length
     */
    double getLength(){
        return length;
    }
}
