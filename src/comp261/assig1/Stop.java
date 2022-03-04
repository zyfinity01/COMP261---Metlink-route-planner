package comp261.assig1;

import java.util.ArrayList;


// decide the data structure for stops
public class Stop {
    //probably always have these three    
    private GisPoint loc;
    private String name;
    private String id;

    //Todo: add additional data structures


        
    // Constructor
        public Stop(String stopID, String stopName, double stopLon, double stopLat) {
            this.id = stopID;
            this.name = stopName;
            this.loc = new GisPoint(stopLon, stopLat);


        }

    // add getters and setters etc
        
    public String getName(){
        return this.name;
    }

    public String getID(){
        return this.id;
    }

    public GisPoint getLoc(){
        return loc;
    }
    
    }
