package resources;

import entities.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ObjectProvider;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CityList implements Initializable {
    public TextField text;
    @FXML
    public Button showSelectedCityBtn;
    public ChoiceBox displayCitesListChoiceBox;
    ObservableList list = FXCollections.observableArrayList();
    private BorderPane rootLayout;
    ObjectProvider objectProvider = new ObjectProvider();
    String selectedCity;

    /**
     * Show a new windows for selection
     * @param actionEvent
     */
    public void showSelectedCity(ActionEvent actionEvent) {
        String selected = (String) displayCitesListChoiceBox.getValue();
        if(selected == null){
            text.setText("Choisissez une ville");
        } else {
            selectedCity = selected;
            changeSceneVToBuildingsListView(actionEvent);
        }
    }

    public void changeSceneVToBuildingsListView(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/CityBuildingList.fxml"));
            Parent viewParent = loader.load();

            Scene cityBuildingList = new Scene(viewParent);

            //Access controller
            CityBuildingList controller = loader.getController();
            controller.initData(selectedCity);

            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(cityBuildingList);

            window.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData(){
        list.removeAll(list);

        List<Object> citiesGetter = objectProvider.getAllCities();
        for(int i = 0; i < citiesGetter.size() ; i++){
            City city = (City) citiesGetter.get(i);
            String cityName = city.getLabel();
            list.addAll(cityName);
        }

        displayCitesListChoiceBox.getItems().addAll(list);
    }
}
