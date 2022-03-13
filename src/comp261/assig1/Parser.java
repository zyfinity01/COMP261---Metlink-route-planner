package comp261.assig1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This utility class parses the files, and return the relevant data structure.
 * Internally it uses BufferedReaders instead of Scanners to read in the files,
 * as Scanners are slow.
 * 
 * @author Simon
 */
public class Parser {

    // read the stop file
    // tab separated stop descriptions
    // stop_id	stop_code	stop_name	stop_desc	stop_lat	stop_lon	zone_id	stop_url	location_type	parent_station	stop_timezone

	public static ArrayList<Stop> parseStops(File nodeFile){
    	// data types to be returned to the graph 
        ArrayList<Stop> stops = new ArrayList<Stop>();
		try {
			// make a reader
			BufferedReader br = new BufferedReader(new FileReader(nodeFile));
			br.readLine(); // throw away the top line of the file
			String line;
			// read in each line of the file
            int count = 0;
			while ((line = br.readLine()) != null) {
				// tokenise the line by splitting it on tabs
				String[] tokens = line.split("[\t]");
                if (tokens.length >= 6) {
                    // process the tokens
                    String stopId = tokens[0];
                    String stopName = tokens[2];
                    double lat = Double.valueOf(tokens[4]);
                    double lon = Double.valueOf(tokens[5]);

                    //stop data stored as stop object
                    stops.add(new Stop(stopId, stopName, lon, lat));                   
                }
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException("file reading failed.");
        }
        return stops;
    }

    // parse the trip file
    // header: stop_pattern_id,stop_id,stop_sequence,timepoint
    public static ArrayList<Trip> parseTrips(File tripFile){

        ArrayList<Trip> trips = new ArrayList<Trip>();
		try {
			// make a reader
			BufferedReader br = new BufferedReader(new FileReader(tripFile));
			br.readLine(); // throw away the top line of the file.
			String line;
			// read in each line of the file

            Map<String, ArrayList<String>> patternIdToStop = new HashMap<String, ArrayList<String>>();
            while ((line = br.readLine()) != null) {
                // tokenise the line by splitting it at ",".
                String[] tokens = line.split("[,]");
                if (tokens.length >= 4) {
                    // process the tokens
                    String stopPatternId = tokens[0];
                    String stopId = tokens[1];
                    int stopSequence = Integer.parseInt(tokens[2]);
                    String timepoint = tokens[3];
                    
                    if(patternIdToStop.get(stopPatternId) == null){                 //If trip, stoplist has not been created then create one
                        patternIdToStop.put(stopPatternId, new ArrayList<String>());
                        patternIdToStop.get(stopPatternId).add(stopId); //adds stop value to newly created list
                    } else{
                        patternIdToStop.get(stopPatternId).add(stopId); //if list already exists, appends value to already created list
                    }
                }
            }
            br.close();

            for(String patternId : patternIdToStop.keySet()){
                trips.add(new Trip(patternId, patternIdToStop.get(patternId)));
            }
        } catch (IOException e) {
            throw new RuntimeException("file reading failed.");
        }
        return trips;
    }
}





// code for COMP261 assignments