package comp261.assig1;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    //Todo add your data structures here

    
// constructor post parsing
    public Graph() {

    }

    // constructor with parsing
    public Graph(File stopFile, File tripFile) {
        //Todo: instantiate your data structures here

        //Then you could parse them using the Parser
        stops = Parser.parseStops(stopFile);
        trips = Parser.parseRoutes(tripFile);

        buildStopList();
        buildTripData();
    }

    // buildStoplist from other data structures
    private void buildStopList() {
       // Todo: you could use this sort of method to create additional data structures

    }

    // buildRouteData into stops
    private void buildTripData(){
        // Todo: this could be used for trips
    }

    // Todo: getters and setters





 

}
