/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs321.group1.oktnav;

import java.util.ArrayList;

/**
 *
 * @author Tobin
 */
/**
 * A class that contains information about rooms
 * @author Marwa
 */
public class Room extends Location {
   
    private final String roomNumber;
    //private final String name;

    /**
     * constructor of the class Room that holds information on the located room
     * @param roomNumber the name of the desired location of the room
     * @param x the x coordinate on the map
     * @param y the y coordinate on the map
     * @param z the z coordinate on the map
     * @param connections an ArrayList<Location> to the connected locations
     * super calls the Location class's constructor to use the given location
     */
    public Room (String id, String roomNumber, int x, int y, int z, ArrayList<Location> connections) {
       super(id, x, y, z, connections);
        this.roomNumber = roomNumber;
    }
    
    /**
     * 
     * @return the room's name and number 
     */
    public String getName(){
        return this.roomNumber;
    }
} 
