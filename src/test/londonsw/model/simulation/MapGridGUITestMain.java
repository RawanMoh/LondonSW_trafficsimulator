package londonsw.model.simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import londonsw.controller.VehicleController;
import londonsw.model.simulation.components.*;
import londonsw.model.simulation.components.vehicles.Car;
import londonsw.view.simulation.CarGUIDecorator;
import londonsw.view.simulation.MapGridGUIDecorator;
import org.reactfx.EventStreams;
import java.time.Duration;

public class MapGridGUITestMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Ticker t = Ticker.getInstance();

        Map map = drawTestMapSimple();

        //Map map = drawTestMapBig();

        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());

        double width = mapGridGUIDecorator.getWidth();
        double height = mapGridGUIDecorator.getHeight();

        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(5/ width,5/height));    //TODO HARDCODE

        GridPane rootGP = mapGridGUIDecorator.drawComponents();

        //Lane L1 = map.getRandomLane();

        Lane L1 = map.getRoads().get(0).getLanes().get(0);

        //Lane L2 = map.getRandomLane();

        //Lane L1 = map.getRoads().get(0).getLanes().get(roadIndex);

        Car C1 = new Car(0,L1);
        //Car C2 = new Car(1,L2);

        CarGUIDecorator CarGUI = new CarGUIDecorator(C1);
        //CarGUIDecorator CarGUI2 = new CarGUIDecorator(C2);

        CarGUI.setGridPane(rootGP);
        //CarGUI2.setGridPane(rootGP);

        CarGUI.setResizeFactor(mapGridGUIDecorator.getResizeFactor());
        //CarGUI2.setResizeFactor(mapGridGUIDecorator.getResizeFactor());

        Pane carPane = new Pane();
        //Pane carPane2 = new Pane();

        //CarGUI2.drawCar();
        CarGUI.drawCar();

        carPane.getChildren().add(CarGUI.getRectangle());
        //carPane.getChildren().add(CarGUI2.getRectangle());

        StackPane sp = new StackPane();

        sp.getChildren().add(rootGP);

        sp.getChildren().add(carPane);
        //sp.getChildren().add(carPane2);

        Scene scene = new Scene(sp);

        //t.start();

        System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY() );
        EventStreams.ticks(Duration.ofMillis(Ticker.getTickInterval()*1))   //needs to be greater than Ticker.getTickInterval
                .subscribe(
                        tick -> {
                            try {

                                VehicleController.moveVehicle(C1, CarGUI, 1);
                                System.out.println(C1.getCurrentCoordinate().getX() + "," + C1.getCurrentCoordinate().getY() );
                                //VehicleController.moveVehicle(C2, CarGUI2, 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });




        /*
        //Create map
        Map map = new Map(20,20);
        //Decorate map to extend to GUI functionality
        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());
        //Always apply resize
        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(5.0/map.getWidth(),5.0/map.getHeight()));
        //Instantiate GridPane that will contain empty map with grass
        GridPane root = mapGridGUIDecorator.drawComponents();
        //Set GridPane to Scene
        Scene scene = new Scene(root);
        */

        primaryStage.setTitle("Map Layout");
        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setResizable(false);

        //primaryStage.setFullScreen(true);

    }

    public Map drawTestMapSimple() throws Exception {

        Map map = new Map(10,10);

        Road r1 = new Road(new Coordinate(2,1), new Coordinate(8,1));
        Road r2 = new Road(new Coordinate(1,2), new Coordinate(1,8));
        Road r3 = new Road(new Coordinate(9,2), new Coordinate(9,8));
        Road r4 = new Road(new Coordinate(2,9), new Coordinate(8,9));



        r1.addLane(new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST));
        r1.addLane(new Lane(r1.getEndLocation() ,r1.getStartLocation(), MapDirection.WEST));

        r2.addLane(new Lane(r2.getEndLocation(),r2.getStartLocation(), MapDirection.NORTH));
        r2.addLane(new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.SOUTH));

        r3.addLane(new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH));
        r3.addLane(new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH));

        r4.addLane(new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.EAST));
        r4.addLane(new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);

        Intersection i1 = new Intersection(new Coordinate(1,1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9,1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        Intersection i3 = new Intersection(new Coordinate(1,9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);
        Intersection i4 = new Intersection(new Coordinate(9,9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);

        return map;
    }

    public Map drawTestMapBig() throws Exception {

        Map map = new Map(18,18);

        Road r1 = new Road(new Coordinate(2,1), new Coordinate(8,1));
        Road r2 = new Road(new Coordinate(1,2), new Coordinate(1,8));
        Road r3 = new Road(new Coordinate(9,2), new Coordinate(9,8));
        Road r4 = new Road(new Coordinate(2,9), new Coordinate(8,9));

        Road r5 = new Road(new Coordinate(10,1), new Coordinate(16,1));
        Road r6 = new Road(new Coordinate(17,2), new Coordinate(17,8));
        Road r7 = new Road(new Coordinate(10,9), new Coordinate(16,9));

        Lane disabledLane = new Lane(r3.getStartLocation(),r3.getEndLocation(), MapDirection.SOUTH);

        //disable lane
        disabledLane.setState(0);

        r1.addLane(new Lane(r1.getStartLocation(),r1.getEndLocation(), MapDirection.EAST));
        r1.addLane(new Lane(r1.getEndLocation() ,r1.getStartLocation(), MapDirection.WEST));

        r2.addLane(new Lane(r2.getEndLocation(),r2.getStartLocation(), MapDirection.NORTH));
        r2.addLane(new Lane(r2.getStartLocation(),r2.getEndLocation(), MapDirection.SOUTH));

        r3.addLane(new Lane(r3.getEndLocation(),r3.getStartLocation(), MapDirection.NORTH));
        r3.addLane(disabledLane);

        r4.addLane(new Lane(r4.getStartLocation(),r4.getEndLocation(), MapDirection.EAST));
        r4.addLane(new Lane(r4.getEndLocation(),r4.getStartLocation(), MapDirection.WEST));

        r5.addLane(new Lane(r5.getStartLocation(),r5.getEndLocation(), MapDirection.EAST));
        r5.addLane(new Lane(r5.getEndLocation(),r5.getStartLocation(), MapDirection.WEST));

        r6.addLane(new Lane(r6.getEndLocation(),r6.getStartLocation(), MapDirection.NORTH));
        r6.addLane(new Lane(r6.getStartLocation(),r6.getEndLocation(), MapDirection.SOUTH));

        r7.addLane(new Lane(r7.getStartLocation(),r7.getEndLocation(), MapDirection.EAST));
        r7.addLane(new Lane(r7.getEndLocation(),r7.getStartLocation(), MapDirection.WEST));

        map.addRoad(r1);
        map.addRoad(r2);
        map.addRoad(r3);
        map.addRoad(r4);
        map.addRoad(r5);
        map.addRoad(r6);
        map.addRoad(r7);

        Intersection i1 = new Intersection(new Coordinate(1,1));
        i1.setEastRoad(r1);
        i1.setSouthRoad(r2);

        Intersection i2 = new Intersection(new Coordinate(9,1));
        i2.setWestRoad(r1);
        i2.setSouthRoad(r3);
        i2.setEastRoad(r5);

        Intersection i3 = new Intersection(new Coordinate(1,9));
        i3.setNorthRoad(r2);
        i3.setEastRoad(r4);

        Intersection i4 = new Intersection(new Coordinate(9,9));
        i4.setNorthRoad(r3);
        i4.setWestRoad(r4);
        i4.setEastRoad(r7);

        Intersection i5 = new Intersection(new Coordinate(17,1));
        i5.setWestRoad(r5);
        i5.setSouthRoad(r6);

        Intersection i6 = new Intersection(new Coordinate(17,9));
        i6.setNorthRoad(r6);
        i6.setWestRoad(r7);

        map.addIntersection(i1);
        map.addIntersection(i2);
        map.addIntersection(i3);
        map.addIntersection(i4);
        map.addIntersection(i5);
        map.addIntersection(i6);

        return map;
    }

    public static void main(String[] args) {
        launch(args);
    }
}