package resources;

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
import java.util.List;

public class BuildingEditDetail {

    public ChoiceBox fxArchitect;
    public TextField fxYear;
    public TextField fxName;
    public TextField fxImage;
    public ChoiceBox fxStyle;
    public ChoiceBox fxType;
    public ChoiceBox fxMaterial;
    public ChoiceBox fxRoofType;
    public ChoiceBox fxFrame;
    public ChoiceBox fxCity;
    ObservableList styleList = FXCollections.observableArrayList();
    ObservableList typeList = FXCollections.observableArrayList();
    ObservableList materialList = FXCollections.observableArrayList();
    ObservableList roofTypeList = FXCollections.observableArrayList();
    ObservableList frameList = FXCollections.observableArrayList();
    ObservableList cityList = FXCollections.observableArrayList();
    public ChoiceBox styleListChoiceBox;
    public ChoiceBox typeListChoiceBox;
    public ChoiceBox materialListChoiceBox;
    public ChoiceBox roofTypeListChoiceBox;
    public ChoiceBox frameListChoiceBox;
    public ChoiceBox cityListChoiceBox;
    ObjectProvider objectProvider = new ObjectProvider();

    private void loadData(){
        List styles = objectProvider.getAllStyles();
        List roofTypes = objectProvider.getAllRoofTypes();
        List types = objectProvider.getAllTypes();
        List material = objectProvider.getAllMaterial();
        List frames = objectProvider.getAllFrames();
        List cities = objectProvider.getAllCities();

        styleList.removeAll(styleList);
//
//        HashSet<String> buildingsList = new HashSet<>();
//        for(int i = 0; i < buildings.size() ; i++){
//            Building building = (Building) buildings.get(i);
//            buildingsList.add(building.getName());
//        }
//        list.addAll(buildingsList);
//        displayBuildingListChoiceBox.getItems().addAll(list);
    }

    public void onClickReturnHome(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/resources/LoginOverview.fxml"));

            Parent viewParent = loader.load();

            Scene cityBuildingList = new Scene(viewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(cityBuildingList);

            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(ActionEvent actionEvent) {
    }
}
