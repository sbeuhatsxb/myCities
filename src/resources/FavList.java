package resources;

import entities.Building;
import entities.City;
import entities.Favlist;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ObjectProvider;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class FavList {
    ObjectProvider objectProvider = new ObjectProvider();
    ObservableList list = FXCollections.observableArrayList();
    public ChoiceBox displayBuildingListChoiceBox;
    City city;
    User user;
    Building building;
    public TextField text;

    public void initData(User currentUser) {
        user = currentUser;
        loadData();
    }

    private void loadData(){
        Favlist favlist = objectProvider.getFavlist(user.getId());
        List<Building> buildings = favlist.getBuildings();
        list.removeAll(list);

        HashSet<String> buildingsList = new HashSet<>();
        for(int i = 0; i < buildings.size() ; i++){
            Building building = (Building) buildings.get(i);
            buildingsList.add(building.getName());
        }
        list.addAll(buildingsList);
        displayBuildingListChoiceBox.getItems().addAll(list);
    }

    /**
     * Get
     * @param actionEvent
     */
    public void showSelectedCity(ActionEvent actionEvent) {
        String selected = (String) displayBuildingListChoiceBox.getValue();
        if(selected == null){
            text.setText("Choisissez un bâtiment");
        } else {
            text.setText(selected);
            building = objectProvider.getBuildingByName(selected);
            changeSceneToBuildingDetailView(actionEvent);
        }
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
}
