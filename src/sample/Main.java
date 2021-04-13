package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DataHandler;
import resources.Env;

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

        database.getAll(ARCHITECT);
        String a = "b";
    }


    public static void main(String[] args) {
        launch(args);
    }
}
