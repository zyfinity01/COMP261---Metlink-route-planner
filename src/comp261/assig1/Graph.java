package comp261.assig1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    //Todo add your data structures here
    private ArrayList<Stop> stops;
    private ArrayList<Trip> trips;
    private ArrayList<Edge> edges;

    private double[][] adjacencyMatrix; // idk

    public HashMap<String, Stop> stopsMap;
    public HashMap<String, Trip> tripsMap;

    
// constructor post parsing
    public Graph() {
    }

    // constructor with parsing
    public Graph(File stopFile, File tripFile) {
        //Todo: instantiate your data structures here
        stops = new ArrayList<Stop>();
        trips = new ArrayList<Trip>();
        //Then you could parse them using the Parser
        this.stops = Parser.parseStops(stopFile);
        this.trips = Parser.parseTrips(tripFile);

        buildStopList();
        buildTripData();
        removeInvalidStops();
        generateEdgeList();
    }



    // buildStoplist from other data structures
    private void buildStopList() {
       // Todo: you could use this sort of method to create additional data structures
       stopsMap = new HashMap<String, Stop>();
       for(Stop stop : stops){
           stopsMap.put(stop.getID(), stop);
       }
    }

    // buildTripData into stops
    private void buildTripData(){
        tripsMap = new HashMap<String, Trip>();
        for(Trip trip : trips){
            tripsMap.put(trip.getTripID(), trip);
        }
        // Todo: this could be used for trips
    }

    private void generateEdgeList() {
        edges = new ArrayList<Edge>();
        for(Trip trip : trips){
            ArrayList<String> stopsOnTrip = trip.getStops();
            for(int i = 0; i < stopsOnTrip.size()-1; i++) {
                edges.add(new Edge(stopsMap.get(stopsOnTrip.get(i)), stopsMap.get(stopsOnTrip.get(i+1)), trip.getTripID()));
                /* //debugging print statements 
                System.out.println("generating edges...");
                System.out.println("trip id: " + trip.getTripID());
                System.out.println("from stop: " + stopsOnTrip.get(i));
                System.out.println("to stop: " + stopsOnTrip.get(i+1));
                */
                if(stopsMap.get(stopsOnTrip.get(i)) == null){
                    System.out.println("NULL FROM STOP -------------------------------------------");
                    System.exit(0);
                }
                if(stopsMap.get(stopsOnTrip.get(i+1)) == null){
                    System.out.println("NULL TO STOP -------------------------------------------");
                    System.exit(0);
                }
            }

        }
    }

    private void removeInvalidStops(){ //removes invalid stops from every trip if it does not belong in the stopMap data structure
        for(Trip trip : trips){
            ArrayList<String> stopsOnTrip = trip.getStops();
            ArrayList<String> removeList = new ArrayList<String>();
            for(int i = 0; i < stopsOnTrip.size(); i++) {
                if(stopsMap.get(stopsOnTrip.get(i)) == null){
                    removeList.add(stopsOnTrip.get(i));
                }
            }
            stopsOnTrip.removeAll(removeList);
        }
    }

        // Todo: getters and setters
    public ArrayList<Stop> getStops(){
        return this.stops;
    }

    public ArrayList<Trip> getTrips(){
        return this.trips;
    }

    public ArrayList<Edge> getEdges(){
        return this.edges;
    }


 

}
