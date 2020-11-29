/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs321.group1.oktnav;
import java.util.ArrayList;

/**
 *
 * @author Annaleise
 */
public class App {
    
      public static void main(String[] args) {
          ArrayList<Location> e = new ArrayList<>();
          
          
          
          Location a = new Location(0, 0, 0, e);
          Location b = new Location(0, 2, 0, e);
          Location c = new Location(1, 1, 0, e);
          Location d = new Location(2, 2, 0, e);
          Location f = new Location(0, 2, 0, e);
          
          a.addConnection(b);
          a.addConnection(f);
          a.addConnection(c);
          
          b.addConnection(d);
          b.addConnection(a);
          
          c.addConnection(a);
          c.addConnection(d);
          
          d.addConnection(b);
          d.addConnection(c);
          d.addConnection(f);
          
          f.addConnection(a);   
          f.addConnection(d);
          
          Navigator nav = new Navigator(new Map(e));
          
          nav.calculatePath(a, d, 0);
          
      }
      
    
}
