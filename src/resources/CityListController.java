package resources;

import entities.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.ObjectProvider;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CityListController implements Initializable {
    public TextField text;
    @FXML
    public Button showSelectedCityBtn;
    public ChoiceBox displayCitesListChoiceBox;
    ObservableList list = FXCollections.observableArrayList();
    private BorderPane rootLayout;

    public void showSelectedCity(ActionEvent actionEvent) {
        String selected = (String) displayCitesListChoiceBox.getValue();
        if(selected == null){
            text.setText("Please select an item");
        } else {
            text.setText(selected);
            showCityOverview();
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData(){
        list.removeAll(list);
        ObjectProvider objectProvider = new ObjectProvider();
        List<Object> citiesGetter = objectProvider.getAllCities();
        for(int i = 0; i < citiesGetter.size() ; i++){
            City city = (City) citiesGetter.get(i);
            String cityName = city.getLabel();
            list.addAll(cityName);
        }

        displayCitesListChoiceBox.getItems().addAll(list);
    }

        public void showCityOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/resources/CityOverview.fxml"));
            AnchorPane cityOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(cityOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
