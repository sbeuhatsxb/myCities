package sample;

import entities.Building;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DataHandler;
import resources.Env;

import java.util.List;

public class Main extends Application implements Env {

    private DataHandler database;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1440, 1024));
        primaryStage.show();

        database = new DataHandler();
        database.newDatabase();

        List<Object> test = database.getAll(Env.ROOF_TYPE);

        List<Object> buildingGetter = database.get(1, Env.BUILDING);
        Building building = (Building) buildingGetter.get(0);

        String a = "b";
    }


    public static void main(String[] args) {
        launch(args);
    }
}
