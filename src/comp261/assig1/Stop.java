package comp261.assig1;

import java.util.ArrayList;


// decide the data structure for stops
public class Stop {
    //probably always have these three    
    private GisPoint loc;
    private String name;
    private String id;


        
    // Constructor
        public Stop(String stopID, String stopName, double stopLon, double stopLat) {
            this.id = stopID;
            this.name = stopName;
            this.loc = new GisPoint(stopLon, stopLat);

        }

    // add getters and setters etc
    
    //Return name of stop
    public String getName(){
        return this.name;
    }

    //Return ID of stop
    public String getID(){
        return this.id;
    }

    //Return gispoint location of stop
    public GisPoint getLoc(){
        return loc;
    }

    //Return nicely formated string of stop
    public String toString(){
        return this.getID() + " " + this.getName() + " at: (" + this.getLoc().lon + ", " + this.getLoc().lat + ")";
    }
    
    }
