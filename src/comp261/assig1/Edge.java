package comp261.assig1;

// The edge class represents an edge in the graph.

public class Edge {
    private Stop fromStop;
    private Stop toStop;
    private String tripId;


    public Edge(Stop fromStop, Stop toStop, String tripID){
        this.fromStop = fromStop;
        this.toStop = toStop;
        this.tripId = tripID;
    }


    //Return the stop object that is the start of the edge
    public Stop getFromStop(){
        return this.fromStop;
    }

    //Return the stop object that is the end of the edge 
    public Stop getToStop(){
        return this.toStop;
    }

    //Return the trip id that this edge occurs on
    public String getTripID(){
        return this.tripId;
    }
  

}
