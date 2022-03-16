package comp261.assig1;

import java.io.File;
import java.util.*;
import java.util.HashMap;

public class Graph {

    // Todo add your data structures here
    private ArrayList<Stop> stops;
    private ArrayList<Trip> trips;
    private ArrayList<Edge> edges;

    public HashMap<String, Stop> stopsMap;
    public HashMap<String, Trip> tripsMap;
    public HashMap<Stop, ArrayList<Stop>> stopToAStops; // map refferring stops to asociated stops within trips
    public HashMap<Stop, ArrayList<Edge>> stopToAEdges; // map refferring stops to asociated stops within trips

    public Trie STNode; // Stop Trie Node

    // constructor post parsing
    public Graph() {
    }

    // constructor with parsing
    public Graph(File stopFile, File tripFile) {
        // Todo: instantiate your data structures here
        stops = new ArrayList<Stop>();
        trips = new ArrayList<Trip>();
        // Then you could parse them using the Parser
        this.stops = Parser.parseStops(stopFile);
        this.trips = Parser.parseTrips(tripFile);

        buildStopList();
        buildTripData();
        removeInvalidStops();
        generateEdgeList();
        populateAssociatedStops();
        generateTrieNode();
    }

    private void populateAssociatedStops() {
        stopToAStops = new HashMap<Stop, ArrayList<Stop>>();
        stopToAEdges = new HashMap<Stop, ArrayList<Edge>>();
        for (Stop stop : stops) {
            ArrayList<Trip> tripsThroughStop = new ArrayList<Trip>();
            for (Trip trip : trips) {
                if (trip.getStops().contains(stop.getID())) {
                    tripsThroughStop.add(trip);
                }
            }

            for (Trip trip : tripsThroughStop) {
                for (String associatedStop : trip.getStops()) {
                    if (!stopToAStops.containsKey(stop)) {
                        stopToAStops.put((stop), new ArrayList<Stop>()); // add stop objects that need to be higlighted
                                                                         // to list
                    }
                    stopToAStops.get(stop).add(stopsMap.get(associatedStop));
                }

                for (Edge edge : edges) {
                    if (edge.getTripID().equals(trip.getTripID())) {
                        if (!stopToAEdges.containsKey(stop)) {
                            stopToAEdges.put((stop), new ArrayList<Edge>()); // add stop objects that need to be
                                                                             // higlighted to list
                        }
                        stopToAEdges.get(stop).add(edge); // add edge objects that need to be higlighted to list
                    }
                }
            }
        }

    }

    /**
     * Build trie data structure with in the STNode object
     */
    private void generateTrieNode() {
        STNode = new Trie();
        for (Stop stop : stops) {
            if (stopToAStops.get(stop) != null) {
                STNode.add(stop.getName().toCharArray(), stop);
            }
            // System.out.println(stop.getName());

        }
        /*
         * List<Stop> stopsmatch = STNode.getAll("Well".toCharArray());
         * 
         * for(Stop stopss : stopsmatch){
         * System.out.println(stopss.getName());
         * }
         */
    }

    // buildStoplist from other data structures
    private void buildStopList() {
        // Todo: you could use this sort of method to create additional data structures
        stopsMap = new HashMap<String, Stop>();
        for (Stop stop : stops) {
            stopsMap.put(stop.getID(), stop);
        }
    }

    // buildTripData into stops
    private void buildTripData() {
        tripsMap = new HashMap<String, Trip>();
        for (Trip trip : trips) {
            tripsMap.put(trip.getTripID(), trip);
        }
        // Todo: this could be used for trips
    }

    private void generateEdgeList() {
        edges = new ArrayList<Edge>();
        for (Trip trip : trips) {
            ArrayList<String> stopsOnTrip = trip.getStops();
            for (int i = 0; i < stopsOnTrip.size() - 1; i++) {
                edges.add(new Edge(stopsMap.get(stopsOnTrip.get(i)), stopsMap.get(stopsOnTrip.get(i + 1)),
                        trip.getTripID()));
            }

        }
        System.out.println(trips.size());
    }

    private void removeInvalidStops() { // removes invalid stops from every trip if it does not belong in the stopMap
                                        // data structure
        for (Trip trip : trips) {
            ArrayList<String> stopsOnTrip = trip.getStops();
            ArrayList<String> removeList = new ArrayList<String>();
            for (int i = 0; i < stopsOnTrip.size(); i++) {
                if (stopsMap.get(stopsOnTrip.get(i)) == null) {
                    removeList.add(stopsOnTrip.get(i));
                }
            }
            stopsOnTrip.removeAll(removeList);
        }
    }

    // Todo: getters and setters
    public ArrayList<Stop> getStops() {
        return this.stops;
    }

    public ArrayList<Trip> getTrips() {
        return this.trips;
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

}
