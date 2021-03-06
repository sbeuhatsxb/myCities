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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
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

    public String selectedYear;
    public String selectedName;
    public String selectedDescription;
    public String selectedImage;
    public String selectedArchitect;
    public String selectedStyle;
    public String selectedType;
    public String selectedMaterial;
    public String selectedRoofType;
    public String selectedFrame;
    public String selectedCity;
    public TextArea fxDescription;
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
        fxDescription.setText(building.getDescription());
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
        Building newBuilding = new Building();

        selectedYear = fxYear.getText();
        selectedName = fxName.getText();
        selectedImage =fxImage.getText();


        selectedArchitect = (String) fxArchitect.getValue();
        selectedStyle = (String) fxStyle.getValue();
        selectedType = (String) fxType.getValue();
        selectedMaterial = (String) fxMaterial.getValue();
        selectedRoofType = (String) fxRoofType.getValue();
        selectedFrame = (String) fxFrame.getValue();
        selectedCity = (String) fxCity.getValue();
        selectedDescription = fxDescription.getText();

        newBuilding.setYear(Integer.valueOf(selectedYear));
        newBuilding.setName(selectedName);
        newBuilding.setImage(selectedImage);
        newBuilding.setDescription(selectedDescription);
        newBuilding.setArchitect(objectProvider.getArchitectByLabel(selectedArchitect));
        newBuilding.setStyle(objectProvider.getStyleByLabel(selectedStyle));
        newBuilding.setType(objectProvider.getTypeByLabel(selectedType));
        newBuilding.setMaterial(objectProvider.getMaterialByLabel(selectedMaterial));
        newBuilding.setRoofType(objectProvider.getRoofTypeByLabel(selectedRoofType));
        newBuilding.setFrame(objectProvider.getFrameByLabel(selectedFrame));
        newBuilding.setCity(objectProvider.getCityByName(selectedCity));


        dbFeeder.addNewBuilding(newBuilding);
        dbFeeder.deleteBuilding(providedBuilding);

    }

    public void setYear(ActionEvent actionEvent) {
        selectedYear = (String) fxYear.getText();
    }

    public void setName(ActionEvent actionEvent) {
        selectedName = (String) fxName.getText();
    }

    public void setImage(ActionEvent actionEvent) {
        selectedImage = (String) fxImage.getText();
    }

    public void setArchitect(ContextMenuEvent mouseEvent) {
        selectedArchitect = (String) fxArchitect.getValue();
    }

    public void setStyle(ContextMenuEvent mouseEvent) {
        selectedStyle = (String) fxStyle.getValue();
    }

    public void setType(ContextMenuEvent mouseEvent) {
        selectedType = (String) fxStyle.getValue();
    }

    public void setMaterial(ContextMenuEvent mouseEvent) {
        selectedMaterial = (String) fxMaterial.getValue();
    }

    public void setRoofType(ContextMenuEvent mouseEvent) {
        selectedRoofType = (String) fxRoofType.getValue();
    }

    public void setFrame(ContextMenuEvent mouseEvent) {
         selectedFrame = (String) fxFrame.getValue();
    }

    public void setCity(ContextMenuEvent mouseEvent) {
         selectedCity = (String) fxCity.getValue();
    }

    public void setDescription(ContextMenuEvent contextMenuEvent) {
        selectedDescription = (String) fxDescription.getText();
    }
}
