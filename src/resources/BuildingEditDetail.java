package resources;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DbFeeder;
import model.ObjectProvider;

import java.io.IOException;
import java.util.ArrayList;
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
    ObservableList roofTypeList = FXCollections.observableArrayList();
    ObservableList materialList = FXCollections.observableArrayList();
    ObservableList frameList = FXCollections.observableArrayList();
    ObservableList cityList = FXCollections.observableArrayList();
    ObservableList architextList = FXCollections.observableArrayList();

    ObjectProvider objectProvider = new ObjectProvider();
    Building providedBuilding;

    public void initData(Building building){
        providedBuilding = building;
        fxName.setText(building.getName());
        fxYear.setText(String.valueOf(building.getYear()));
        fxImage.setText(building.getImage());
        fxStyle.setValue(building.getStyle().getLabel());
        fxType.setValue(building.getType().getLabel());
        fxMaterial.setValue(building.getMaterial().getLabel());
        if(building.getRoofType() != null){
            fxRoofType.setValue(building.getRoofType().getLabel());
        }
        fxFrame.setValue(building.getFrame().getLabel());
        fxCity.setValue(building.getCity().getLabel());
        fxArchitect.setValue(building.getArchitect().getLabel());
    }

    public void loadData(){
        List styles = objectProvider.getAllStyles();
        List roofTypes = objectProvider.getAllRoofTypes();
        List types = objectProvider.getAllTypes();
        List materials = objectProvider.getAllMaterial();
        List frames = objectProvider.getAllFrames();
        List cities = objectProvider.getAllCities();
        List architects = objectProvider.getAllArchitects();

        List<String> stylesStrings = new ArrayList<>();
        List<String> roofTypeStrings = new ArrayList<>();
        List<String> typesStrings = new ArrayList<>();
        List<String> materialStrings = new ArrayList<>();
        List<String> frameStrings = new ArrayList<>();
        List<String> cityString = new ArrayList<>();
        List<String> architectString = new ArrayList<>();

        styleList.removeAll(styleList);
        for(int i = 0; i < styles.size() ; i++){
            Style style = (Style) styles.get(i);
            stylesStrings.add(style.getLabel());
        }
        styleList.addAll(stylesStrings);
        fxStyle.getItems().addAll(styleList);

        roofTypeList.removeAll(roofTypeList);
        for(int i = 0; i < roofTypes.size() ; i++){
            RoofType roofType = (RoofType) roofTypes.get(i);
            roofTypeStrings.add(roofType.getLabel());
        }
        roofTypeList.addAll(roofTypeStrings);
        fxRoofType.getItems().addAll(roofTypeList);

        typeList.removeAll(typeList);
        for(int i = 0; i < types.size() ; i++){
            Type type = (Type) types.get(i);
            typesStrings.add(type.getLabel());
        }
        typeList.addAll(typesStrings);
        fxType.getItems().addAll(typeList);


        materialList.removeAll(materialList);
        for(int i = 0; i < materials.size() ; i++){
            Material material = (Material) materials.get(i);
            materialStrings.add(material.getLabel());
        }
        materialList.addAll(materialStrings);
        fxMaterial.getItems().addAll(materialList);

        frameList.removeAll(frameList);
        for(int i = 0; i < frames.size() ; i++){
            Frame frame = (Frame) frames.get(i);
            frameStrings.add(frame.getLabel());
        }
        frameList.addAll(frameStrings);
        fxFrame.getItems().addAll(frameList);

        cityList.removeAll(cityList);
        for(int i = 0; i < cities.size() ; i++){
            City city = (City) cities.get(i);
            cityString.add(city.getLabel());
        }
        cityList.addAll(cityString);
        fxCity.getItems().addAll(cityList);

        architextList.removeAll(architextList);
        for(int i = 0; i < architects.size() ; i++){
            Architect architect = (Architect) architects.get(i);
            architectString.add(architect.getLabel());
        }
        architextList.addAll(architectString);
        fxArchitect.getItems().addAll(architextList);
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
        DbFeeder dbFeeder = new DbFeeder();
        dbFeeder.addNewBuilding(providedBuilding);
        dbFeeder.deleteBuilding(providedBuilding);
    }

    public void setYear(ActionEvent actionEvent) {
        String selectedYear = (String) fxYear.getText();
        providedBuilding.setYear(Integer.valueOf(selectedYear));
    }

    public void setName(ActionEvent actionEvent) {
        String selectedName = (String) fxName.getText();
        providedBuilding.setName(selectedName);
    }

    public void setImage(ActionEvent actionEvent) {
        String selectedImage = (String) fxImage.getText();
        providedBuilding.setImage(selectedImage);
    }

    public void setArchitect(MouseEvent mouseEvent) {
        String selectedArchitect = (String) fxArchitect.getValue();
        providedBuilding.setArchitect(objectProvider.getArchitectByLabel(selectedArchitect));
    }

    public void setStyle(MouseEvent mouseEvent) {
        String selectedStyle = (String) fxStyle.getValue();
        providedBuilding.setStyle(objectProvider.getStyleByLabel(selectedStyle));
    }

    public void setType(MouseEvent mouseEvent) {
        String selectedType = (String) fxStyle.getValue();
        providedBuilding.setType(objectProvider.getTypeByLabel(selectedType));
    }

    public void setMaterial(MouseEvent mouseEvent) {
        String selectedMaterial = (String) fxMaterial.getValue();
        providedBuilding.setMaterial(objectProvider.getMaterialByLabel(selectedMaterial));
    }

    public void setRoofType(MouseEvent mouseEvent) {
        String selectedRoofType = (String) fxRoofType.getValue();
        providedBuilding.setRoofType(objectProvider.getRoofTypeByLabel(selectedRoofType));
    }

    public void setFrame(MouseEvent mouseEvent) {
        String selectedFrame = (String) fxFrame.getValue();
        providedBuilding.setFrame(objectProvider.getFrameByLabel(selectedFrame));
    }

    public void setCity(MouseEvent mouseEvent) {
        String selectedCity = (String) fxCity.getValue();
        providedBuilding.setCity(objectProvider.getCityByName(selectedCity));
    }
}
