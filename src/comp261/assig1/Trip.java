package comp261.assig1;

import java.util.ArrayList;

public class Trip {
    private String stop_pattern_id;
    private String tripId;
    ArrayList<String> stops;


    public Trip(String stopPatternID, ArrayList<String> stops){
        this.stop_pattern_id = stopPatternID;
        this.tripId = stopPatternID;
        this.stops = stops;
    }

    //Return the list of stops within the trip
    public ArrayList<String> getStops(){
        return this.stops;
    }
    
    //Return the trip id of the trip
    public String getTripID(){
        return this.tripId;
    }

}
