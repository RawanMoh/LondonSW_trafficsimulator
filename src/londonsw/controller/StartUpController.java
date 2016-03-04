package londonsw.controller;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.layout.AnchorPane;

import londonsw.model.simulation.Map;
import londonsw.view.simulation.MapGridGUIDecorator;
import londonsw.model.simulation.components.ResizeFactor;

//import java.awt.*;
import java.io.IOException;

public class StartUpController extends Application{

    public Label londonSWlabel;
    public Label trafficSimLabel;
    public Button startButton;

    @FXML private TextField width;

    @FXML private TextField height;

    public void startSoftware(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/startup/StartScreen.fxml"));
        stage.setTitle("LondonSW Traffic Simulator");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }


    public void goToChooseModeScreen(ActionEvent actionEvent) throws IOException {
        Parent chooseModeScreen = FXMLLoader.load(getClass().getResource("../view/startup/ChooseModeScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(chooseModeScreen));
    }

    public void goToSimulationMode(ActionEvent actionEvent) throws Exception {
        // TODO This is just dummy code to take place of the next screens for now

        //Choose Map
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());

        Map map =Map.loadMap(file.getName());

        Parent simulationModeScreen  = FXMLLoader.load(getClass().getResource("../view/startup/SimulationMode" + ".fxml"));

        Node node = simulationModeScreen.lookup("#Scene");
        Pane p = (Pane) node;


        //get Grid Pane
       //Circle c = new Circle(10,100,10);
        //p.getChildren().add(c);


        //Create map
       // Map map = new Map(20,20);
        //Decorate map to extend to GUI functionality
        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());
        //Always apply resize
         mapGridGUIDecorator.setResizeFactor(new ResizeFactor(5.0/map.getWidth(),5.0/map.getHeight()));
        //Instantiate GridPane that will contain empty map with grass
        GridPane root = mapGridGUIDecorator.drawComponents();


        root.scaleShapeProperty();
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);

        //Set GridPane to Scene
       // Scene scene = new Scene(root);
        p.getChildren().add(root);


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(simulationModeScreen));
        //Alert alert;
        //alert = new Alert(Alert.AlertType.INFORMATION);

    }

    public void goToMapBuilderMode(ActionEvent actionEvent) throws IOException {

        Parent setMapDimension = FXMLLoader.load(getClass().getResource("../view/startup/MapDimension" + ".fxml"));

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(setMapDimension));

    }
    public void goToMapCreationScreen(ActionEvent actionEvent) throws Exception {

        //String mapWidth=test


        Parent mapCreation = FXMLLoader.load(getClass().getResource("../view/mapcreation/MapCreation" + ".fxml"));
        int w = Integer.parseInt(width.getText());
        int h= Integer.parseInt(height.getText());


        Map map = new Map(w,h);
        //Decorate map to extend to GUI functionality
        MapGridGUIDecorator mapGridGUIDecorator = new MapGridGUIDecorator(map.getGrid());
        //Always apply resize
        mapGridGUIDecorator.setResizeFactor(new ResizeFactor(5.0/map.getWidth(),5.0/map.getHeight()));
        //Instantiate GridPane that will contain empty map with grass
        GridPane root = mapGridGUIDecorator.drawComponents();

        GridPane mapGrid =(GridPane) mapCreation.lookup("mapView");
        mapGrid.getChildren().add(root);


        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(mapCreation));
    }
}
