
package cs321.group1.oktnav;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author Annaleise
 * 
 * The path class is just a basic struct-like class to hold a location path and it's length
 */
public class Path {
    private ArrayList<Location> path;
    private double length;
    
    /**
     * Constructs a Path object
     * @param p the ArrayList of locations representing the path
     * @param l the length of the path
     */
    Path( ArrayList<Location> p, double l ){
        path = p;
        length = l;
    }
    
    /**
     * Returns the location path.
     * @return the path
     */
    public ArrayList<Location> getPath(){
        return path;
    }
    
    /**
     * Returns the length of the path.
     * @return path length
     */
    public double getLength(){
        return length;
    }
    
    /**
     * Creates JSON representation of the Path object for use by the frontend.
     * @return the JSON representation of the path
     */
    public JSONObject getJSON() {
        JSONObject jsonBuilder = new JSONObject();
        
        jsonBuilder.put("length", length);
        JSONObject[] p = new JSONObject[path.size()];
        for (int i = 0; i < p.length; i++)
            p[i] = path.get(i).getJSON();
        jsonBuilder.put("path", p);
        
        return jsonBuilder;
    }
}