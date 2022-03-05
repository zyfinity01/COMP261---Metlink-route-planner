package comp261.assig1;

import java.util.ArrayList;

public class Trip {
    private String stop_pattern_id;
    private String tripId;
    ArrayList<String> stops;


    public Trip(String stopPatternID, String tripID, ArrayList<String> stops){
        this.stop_pattern_id = stopPatternID;
        this.tripId = tripID;
        this.stops = stops;
    }

}
