package comp261.assig1;

// The edge class represents an edge in the graph.

public class Edge {
    private Stop fromStop;
    private Stop toStop;
    private String tripId;

    //todo: add a constructor

    public Edge(Stop fromStop, Stop toStop, String tripID){
        this.fromStop = fromStop;
        this.toStop = toStop;
        this.tripId = tripID;
    }

    //todo: add getters and setters

    public Stop getFromStop(){
        return this.fromStop;
    }

    public Stop getToStop(){
        return this.toStop;
    }

    public String getTripID(){
        return this.tripId;
    }
  

}
