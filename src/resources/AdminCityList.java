package resources;

import entities.Building;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ObjectProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminCityList {
    public TextField text;
    @FXML
    public Button showSelectedCityBtn;
    public Button fxReturnHome;
    public ChoiceBox displayBuildingListChoiceBox;
    ObservableList list = FXCollections.observableArrayList();
    ObjectProvider objectProvider = new ObjectProvider();
    String selectedBuilding;


    public void initData() {
        loadData();
    }

    public void showSelectedCity(ActionEvent actionEvent) {
        String selected = (String) displayBuildingListChoiceBox.getValue();
        if (selected == null) {
            text.setStyle("-fx-text-fill: red;");
            text.setText("Veuillez choisir une ville");
        } else {
            selectedBuilding = selected;
            changeSceneToAdminDetailView(actionEvent);
        }
    }

    public void changeSceneToAdminDetailView(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/BuildingEditDetail.fxml"));
            Parent viewParent = loader.load();

            Scene cityBuildingList = new Scene(viewParent);

            //Access controller
            BuildingEditDetail controller = loader.getController();
            Building building = objectProvider.getBuildingByName(selectedBuilding);
            controller.initData(building);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(cityBuildingList);

            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadData() {
        list.removeAll(list);

        List<Object> buildingGetter = objectProvider.getAllBuildings();

        List<String> buildings = new ArrayList<>();

        String buildingName;

        for (int i = 0; i < buildingGetter.size(); i++) {
            Building building = (Building) buildingGetter.get(i);
            buildingName = building.getName();
            buildings.add(buildingName);
        }

        list.addAll(buildings);
        displayBuildingListChoiceBox.getItems().addAll(list);
    }

    public void onClickBuildingList(MouseEvent mouseEvent) {
    }
}
