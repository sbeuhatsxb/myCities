package resources;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ObjectProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityBuildingList {
    public ChoiceBox typeFilter;
    public ChoiceBox styleFilter;
    public Button showSelectedCityBtn;
    ObjectProvider objectProvider = new ObjectProvider();
    public ObservableList<Object> list = FXCollections.observableArrayList();
    public ObservableList<Object> styleObs = FXCollections.observableArrayList();
    public ObservableList<Object> typeObs = FXCollections.observableArrayList();
    public ChoiceBox displayBuildingListChoiceBox;
    City city;
    User user;
    Building building;
    public TextField text;

    public void initData(String selectedCity, User currentUser) {
        user = currentUser;
        city = objectProvider.getCityByName(selectedCity);
        loadData();
    }

    /**
     * Get
     * @param actionEvent
     */
    public void showSelectedCity(ActionEvent actionEvent) {
        String selected = (String) displayBuildingListChoiceBox.getValue();
        if(selected == null){
            text.setStyle("-fx-text-fill: red;");
            text.setText("Veuillez choisir un bâtiment");
        } else {
            text.setText(selected);
            building = objectProvider.getBuildingByName(selected);
            changeSceneToBuildingDetailView(actionEvent);
        }
    }

    @FXML
    private void onClickBuildingList(){
        if(styleFilter.getValue() != null && typeFilter.getValue() != null){
            List<Object> buildings;
            displayBuildingListChoiceBox.getSelectionModel().clearSelection();
            displayBuildingListChoiceBox.getItems().clear();
            buildings = objectProvider.getBuildingsByCity(city);
            list.removeAll(list);
            List<String> buildingsList = new ArrayList<>();
            for(int i = 0; i < buildings.size() ; i++){
                Building building = (Building) buildings.get(i);
                if(building.getType().getLabel().equals(typeFilter.getValue()) && building.getStyle().getLabel().equals(styleFilter.getValue())){
                    buildingsList.add(building.getName());
                    continue;
                }
            }
            list.addAll(buildingsList);
            displayBuildingListChoiceBox.getItems().addAll(list);
        } else if(styleFilter.getValue() != null || typeFilter.getValue() != null){
            List<Object> buildings;
            displayBuildingListChoiceBox.getSelectionModel().clearSelection();
            displayBuildingListChoiceBox.getItems().clear();
            buildings = objectProvider.getBuildingsByCity(city);
            list.removeAll(list);
            List<String> buildingsList = new ArrayList<>();
            for(int i = 0; i < buildings.size() ; i++){
                Building building = (Building) buildings.get(i);
                if(building.getType().getLabel().equals(typeFilter.getValue())){
                    buildingsList.add(building.getName());
                    continue;
                }
                if(building.getStyle().getLabel().equals(styleFilter.getValue())){
                    buildingsList.add(building.getName());
                    continue;
                }
            }
            list.addAll(buildingsList);
            displayBuildingListChoiceBox.getItems().addAll(list);
        };
        if(displayBuildingListChoiceBox.getItems().size() == 0){
            text.setText("Vos critères n'ont renvoyé aucun résultat");
        };
    }

    private void loadData(){

        List<Object> types = objectProvider.getAllTypes();
        List<Object> styles = objectProvider.getAllStyles();

        List<Object> buildings = objectProvider.getBuildingsByCity(city);
        list.removeAll(list);
        List<String> buildingsList = new ArrayList<>();
        for(int i = 0; i < buildings.size() ; i++){
            Building building = (Building) buildings.get(i);
            buildingsList.add(building.getName());
        }
        list.addAll(buildingsList);
        displayBuildingListChoiceBox.getItems().addAll(list);

        typeObs.removeAll(typeObs);
        List<String> typesList = new ArrayList<>();
        typesList.add("");
        for(int i = 0; i < types.size() ; i++){
            Type type = (Type) types.get(i);
            typesList.add(type.getLabel());
        }
        typeObs.addAll(typesList);
        typeFilter.getItems().addAll(typeObs);

        styleObs.removeAll(styleObs);
        List<String> stylesList = new ArrayList<>();
        stylesList.add("");
        for(int i = 0; i < styles.size() ; i++){
            Style style = (Style) styles.get(i);
            stylesList.add(style.getLabel());
        }
        styleObs.addAll(stylesList);
        styleFilter.getItems().addAll(styleObs);

    }

    public void changeSceneToBuildingDetailView(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/BuildingDetail.fxml"));
            Parent viewParent = loader.load();

            Scene buildingDetail = new Scene(viewParent);

            //Access controller
            BuildingDetail controller = loader.getController();
            controller.initData(building, user);

            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(buildingDetail);

            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onClickReturnHome(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/resources/CityList.fxml"));

            Parent viewParent = loader.load();

            Scene cityBuildingList = new Scene(viewParent);
            CityList controleur =  loader.getController();
            controleur.initData(user);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(cityBuildingList);

            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
