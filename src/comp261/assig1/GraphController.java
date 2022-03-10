package comp261.assig1;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.util.ArrayList;

import javafx.event.*;

public class GraphController {

    // names from the items defined in the FXML file
    @FXML private TextField searchText; 
    @FXML private Button load;
    @FXML private Button quit;
    @FXML private Button up;
    @FXML private Button down;
    @FXML private Button left;
    @FXML private Button right;
    @FXML private Canvas mapCanvas;
    @FXML private Label nodeDisplay;
    @FXML private TextArea tripText;

    // These are use to map the nodes to the location on screen
    private Double scale = 5000.0; //5000 gives 1 pixel ~ 2 meter
    private static final double ratioLatLon = 0.73; // in Wellington ratio of latitude to longitude
    private GisPoint mapOrigin = new GisPoint(174.77, -41.3); // Lon Lat for Wellington
 
    private static int stopSize = 5; // drawing size of stops
    private static int moveDistance = 100; // 100 pixels
    private static double zoomFactor = 1.1; // zoom in/out factor

    private ArrayList<Stop> highlightNodes = new ArrayList<Stop>(); //all stop objects in this list will be highlighted
    private ArrayList<Edge> highlightEdges = new ArrayList<Edge>(); //all edge objects in this list will be highlighted


    // map model to screen using scale and origin
    private Point2D model2Screen (GisPoint modelPoint) {
        return new Point2D(model2ScreenX(modelPoint.lon), model2ScreenY(modelPoint.lat));
    }
    private double model2ScreenX (double modelLon) {
      return (modelLon - mapOrigin.lon) * (scale*ratioLatLon) + mapCanvas.getWidth()/2; 
    }
    // the getHeight at the start is to flip the Y axis for drawing as JavaFX draws from the top left with Y down.
    private double model2ScreenY (double modelLat) {
      return mapCanvas.getHeight()-((modelLat - mapOrigin.lat)* scale + mapCanvas.getHeight()/2);
    }

    // map screen to model using scale and origin
    private double getScreen2ModelX(Point2D screenPoint) {
        return (((screenPoint.getX()-mapCanvas.getWidth()/2)/(scale*ratioLatLon)) + mapOrigin.lon);
    }
    private double getScreen2ModelY(Point2D screenPoint) {
        return ((((mapCanvas.getHeight()-screenPoint.getY())-mapCanvas.getHeight()/2)/scale) + mapOrigin.lat);
    }
    
    private GisPoint getScreen2Model(Point2D screenPoint) {
        return new GisPoint(getScreen2ModelX(screenPoint), getScreen2ModelY(screenPoint));
    }   

    

    public void initialize() {
       // currently blank
    }

    /* handle the load button being pressed connected using FXML*/
    public void handleLoad(ActionEvent event) {
        Stage stage = (Stage) mapCanvas.getScene().getWindow();
        System.out.println("Handling event " + event.getEventType());
        FileChooser fileChooser = new FileChooser();
        //Set to user directory or go to default if cannot access

        
        File defaultNodePath = new File("data");
        if(!defaultNodePath.canRead()) {
            defaultNodePath = new File("C:/");
        }
        fileChooser.setInitialDirectory(defaultNodePath);
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extentionFilter);

        fileChooser.setTitle("Open Stop File");
        File stopFile = fileChooser.showOpenDialog(stage);

        fileChooser.setTitle("Open Stop Pattern File");
        File tripFile = fileChooser.showOpenDialog(stage);

        Main.graph=new Graph(stopFile,tripFile);
        drawGraph();
        event.consume(); // this prevents other handlers from being called
    }

    public void handleQuit(ActionEvent event) {
        System.out.println("Quitting with event " + event.getEventType()); 
        event.consume();  
        System.exit(0); 
    }

    public void handleZoomin(ActionEvent event) {
        System.out.println("Zoom in event " + event.getEventType()); 
        scale *= zoomFactor;
        drawGraph();
        event.consume();  
    }

    public void handleZoomout(ActionEvent event) {
        System.out.println("Zoom out event " + event.getEventType()); 
        scale *= 1.0/zoomFactor;
        drawGraph();
        event.consume();  
    }

    public void handleUp(ActionEvent event) {
        System.out.println("Move up event " + event.getEventType()); 
        mapOrigin.add(0, moveDistance/scale);
        drawGraph();
        event.consume();  
    }

    public void handleDown(ActionEvent event) {
        System.out.println("Move Down event " + event.getEventType()); 
        mapOrigin.subtract(0, moveDistance/scale);
        drawGraph();
        event.consume();  
    }

    public void handleLeft(ActionEvent event) {
        System.out.println("Move Left event " + event.getEventType()); 
        mapOrigin.add(moveDistance/scale, 0);
        drawGraph();
        event.consume();  
    }

    public void handleRight(ActionEvent event) {
        System.out.println("Move Right event " + event.getEventType()); 
        mapOrigin.subtract(moveDistance/scale, 0);
        drawGraph();
        event.consume();  
    }

    public void handleSearch(ActionEvent event) {
        System.out.println("Look up event " + event.getEventType()+ "  "+searchText.getText()); 
        String search = searchText.getText();
        Stop stopMatched = null;
        for(Stop stop : Main.graph.getStops()){
            if(stop.getName().equalsIgnoreCase(search)){
                stopMatched = stop;
            }
        }
        if(stopMatched != null){
            highlightClosestStop(stopMatched.getLoc().lon, stopMatched.getLoc().lat);
        }
 // Todo: figure out how to add searching and by text
 // This is were a Trie would be used potentially
        
        event.consume();  
    }  

    public void handleSearchKey(KeyEvent event) {
        System.out.println("Look up event " + event.getEventType()+ "  "+searchText.getText()); 
        String search = searchText.getText();
// Todo: figure out how to add searching and by text after each keypress
// This is were a Trie would be used potentially
        event.consume();  
    }  




/* handle mouse clicks on the canvas
   select the node closest to the click */
    public void handleMouseClick(MouseEvent event) {
        System.out.println("Mouse click event " + event.getEventType());
        if(event.getEventType() == event.MOUSE_CLICKED){
            Point2D mouseLocation = new Point2D(event.getX(), event.getY());
            highlightClosestStop(getScreen2ModelX(mouseLocation), getScreen2ModelY(mouseLocation));
        }


// Todo: find node closed to mouse click
// event.getX(), event.getY() are the mouse coordinates
       
        event.consume();
    }


    /* find the Closest stop to the lon,lat postion */
    public void highlightClosestStop(double lon, double lat) {
        highlightNodes.clear();
        highlightEdges.clear();
        double minDist = Double.MAX_VALUE;
        Stop closestStop = null;
        for(Stop stop : Main.graph.getStops()){
            double distance = Math.hypot(lon - stop.getLoc().lon, lat - stop.getLoc().lat);
            if(distance < minDist){
                minDist = distance;
                closestStop = stop;
            }
        }
        ArrayList<Trip> tripsThroughStop = findTripsThroughStop(closestStop);
        for(Trip trip : tripsThroughStop){
            for(Edge edge : Main.graph.getEdges()){
                if(edge.getTripID().equals(trip.getTripID())){
                    highlightEdges.add(edge);                      //add edge objects that need to be higlighted to list
                }
            }
            tripText.clear(); //clear text canvas
            for(String stop : trip.getStops()){
                tripText.appendText(Main.graph.stopsMap.get(stop).toString()); //output to the screen all the stops that are highlighted
                tripText.appendText("\n");
                highlightNodes.add(Main.graph.stopsMap.get(stop)); //add stop objects that need to be higlighted to list
            }
        }
        drawGraph();
        //Todo: find closest stop and work out how to highlight it
        //Todo: Work out highlighting the trips through this node
    }

    /* 
    Returns an arraylist of the trip objects that include
    the stop provided in the parameter
    */
    public ArrayList<Trip> findTripsThroughStop(Stop stop){
        ArrayList<Trip> tripsThroughStop = new ArrayList<Trip>();
        for(Trip trip : Main.graph.getTrips()){
            if(trip.getStops().contains(stop.getID())){
                tripsThroughStop.add(trip);
            }
        }
        return tripsThroughStop;
    }

    

/*
Drawing the graph on the canvas
*/
    public void drawCircle(double x, double y, int radius) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.fillOval(x-radius/2, y-radius/2, radius, radius);
    }

    public void drawLine(double x1, double y1, double x2, double y2) {
        mapCanvas.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
    }
    

    // This will draw the graph in the graphics context of the mapcanvas
    public void drawGraph() {
        Graph graph = Main.graph;
        if(graph == null) {
            return;
        }
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        // Todo:  store node list so we can use nodes to find edge end points.
        
        // Todo: use the nodes form the data in graph to draw the graph
        // probably use something like this
        for(Stop stop : graph.getStops()){
            Point2D screenPoint = model2Screen(stop.getLoc());
            int size = 5;
            if(highlightNodes.contains(stop)){
                gc.setFill(Color.RED);
                drawCircle(screenPoint.getX(), screenPoint.getY(), size * 2);

            } else {
                gc.setFill(Color.BLUE);
                drawCircle(screenPoint.getX(), screenPoint.getY(), size);
            }
        }

        //draw edges

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        for(Edge edge : graph.getEdges()){  //Todo: use the edge form the data in graph to draw the graph
            //Todo: step through the edges and draw them with something like
            gc.setStroke(Color.BLACK);
            if(highlightEdges.contains(edge)){
                gc.setStroke(Color.RED);
            }
            Point2D startPoint = model2Screen(edge.getFromStop().getLoc());
            Point2D endPoint = model2Screen(edge.getToStop().getLoc());
            drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
        }
                
    }


    private void drawTrip(Trip trip, GraphicsContext gc, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(2);
//Todo: step through a trip to highlight it in a different colour
                //Point2D startPoint = model2Screen(fromStop.getPoint());
                //Point2D endPoint = model2Screen(toStop.getPoint());
                //drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

}
