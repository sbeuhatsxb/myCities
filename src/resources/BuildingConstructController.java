package resources;



import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import entities.Architect;
import entities.Building;
import entities.City;
import entities.Frame;
import entities.Material;
import entities.RoofType;
import entities.Style;
import entities.Type;
import entities.User;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.DbFeeder;
import model.ObjectProvider;

public class BuildingConstructController {
	private Building building  = new Building();
	private ObjectProvider op = new ObjectProvider();
	ObservableList cityList = FXCollections.observableArrayList();
	ObservableList architectList = FXCollections.observableArrayList();
	ObservableList typeList = FXCollections.observableArrayList();
	ObservableList materialList = FXCollections.observableArrayList();
	ObservableList toitList = FXCollections.observableArrayList();
	ObservableList charpenteList = FXCollections.observableArrayList();
	ObservableList styleList = FXCollections.observableArrayList();
	
    ObjectProvider objectProvider = new ObjectProvider();
    String selectedCity;
	  @FXML
	    private TextArea fxDesc;

	    @FXML
	    private ChoiceBox<String>  fxCity;

	    @FXML
	    private ChoiceBox<?> fxArchitect;

	    @FXML
	    private ChoiceBox<?> fxType;

	    @FXML
	    private ChoiceBox<?> fxMaterial;

	    @FXML
	    private ChoiceBox<?> fxToit;

	    @FXML
	    private ChoiceBox<?> fxFrame;

	    @FXML
	    private ChoiceBox<?> fxStyle;

	    @FXML
	    private TextField fxName;

	    @FXML
	    private TextField fxYear;

	    @FXML
	    private TextField fxWindows;

	    @FXML
	    private ImageView fxImg;

	    @FXML
	    private Button fxInsert;
	    
	    @FXML
	    private Label fxStatus;
	    
	    @FXML
	    private Button fxRetour;
	    
	    @FXML
	    private Button fxNewAjout;

    @FXML
    void insertion(ActionEvent event) {
    	
    	
    	
    	
    	building.setName(fxName.getText());
    	building.setDescription(fxDesc.getText());
    	building.setYear(Integer.parseInt(fxYear.getText()));
    	building.setWindows(Integer.parseInt(fxWindows.getText()));
    	building.setCity(objectProvider.getCityByName(fxCity.getValue().toString()));
    	building.setArchitect(objectProvider.getArchitectByLabel( fxArchitect.getValue().toString()));
    	building.setMaterial(objectProvider.getMaterialByLabel((String) fxMaterial.getValue()));
    	building.setType(objectProvider.getTypeByLabel((String) fxType.getValue()));
    	building.setRoofType(objectProvider.getRoofTypeByLabel((String) fxToit.getValue()));
    	building.setFrame(objectProvider.getFrameByLabel((String) fxFrame.getValue()));
    	building.setStyle(objectProvider.getStyleByLabel((String) fxStyle.getValue()));
    	DbFeeder dbfeeder = new DbFeeder(); 
    	dbfeeder.addNewBuilding(building);
    	fxStatus.setText("Batiment "+fxName.getText()+" est créé");
    	fxInsert.setVisible(false);
    	fxNewAjout.setVisible(true);
    }
    
    @FXML
    public void initialize() {
        loadData();
       
    }
    
    private void loadData() {

    	 cityList.removeAll(cityList);
    	 
    	 

         List<Object> citiesGetter = objectProvider.getAllCities();
         HashSet<String> cities = new HashSet<>();
         String cityName;
         for (int i = 0; i < citiesGetter.size(); i++) {
             City city = (City) citiesGetter.get(i);
             cityName = city.getLabel();
             cities.add(cityName);
         }

         cityList.addAll(cities);
         fxCity.getItems().addAll(cityList);
         
         
         	
         architectList.removeAll(architectList);
    	
         List<Object> architectGetter = objectProvider.getAllArchitects();
         HashSet<String> architects = new HashSet<>();
         String architectName;
         for (int i = 0; i < architectGetter.size(); i++) {
             Architect architect = (Architect) architectGetter.get(i);
             architectName = architect.getLabel();
             architects.add(architectName);
         }

         architectList.addAll(architects);
         fxArchitect.getItems().addAll(architectList);
         
         typeList.removeAll(typeList);
     	
         List<Object> typeGetter = objectProvider.getAllTypes();
         HashSet<String> types = new HashSet<>();
         String typeName;
         for (int i = 0; i < typeGetter.size(); i++) {
             Type type = (Type) typeGetter.get(i);
             typeName = type.getLabel();
             types.add(typeName);
         }

         typeList.addAll(types);
         fxType.getItems().addAll(typeList);
         
         materialList.removeAll(materialList);
      	
         List<Object> materialGetter = objectProvider.getAllMaterial();
         HashSet<String> materials = new HashSet<>();
         String materialName;
         for (int i = 0; i < materialGetter.size(); i++) {
             Material material = (Material) materialGetter.get(i);
             materialName = material.getLabel();
             materials.add(materialName);
         }

         materialList.addAll(materials);
         fxMaterial.getItems().addAll(materialList);
       
         toitList.removeAll(toitList);
        	
         List<Object>toitGetter = objectProvider.getAllRoofTypes();
         HashSet<String> toits = new HashSet<>();
         String toitName;
         for (int i = 0; i < toitGetter.size(); i++) {
             RoofType toit = (RoofType) toitGetter.get(i);
             toitName = toit.getLabel();
             toits.add(toitName);
         }

         toitList.addAll(toits);
         fxToit.getItems().addAll(toitList);
         
     
         
         charpenteList.removeAll(charpenteList);
        	
         List<Object> frameGetter = objectProvider.getAllFrames();
         HashSet<String> charpentes = new HashSet<>();
         String charpenteName;
         for (int i = 0; i < frameGetter.size(); i++) {
             Frame charpente = (Frame) frameGetter.get(i);
             charpenteName = charpente.getLabel();
             charpentes.add(charpenteName);
         }

         charpenteList.addAll(charpentes);
         fxFrame.getItems().addAll(charpenteList);
         
         
         
     //
         
        styleList.removeAll(styleList);
        	
         List<Object> styleGetter = objectProvider.getAllStyles();
         HashSet<String> styles = new HashSet<>();
         String styleName;
         for (int i = 0; i < styleGetter.size(); i++) {
             Style style = (Style) styleGetter.get(i);
             styleName = style.getLabel();
             styles.add(styleName);
         }

         styleList.addAll(styles);
         fxStyle.getItems().addAll(styleList);
    }

    @FXML
    private void retourControlPan(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader();
            
             loader.setLocation(getClass().getResource("/resources/AdminPanel.fxml"));
            
           
            Parent viewParent = loader.load();

            Scene cityBuildingList = new Scene(viewParent);
  
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(cityBuildingList);

            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
    @FXML
    private void ajouterNewBat(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader();
            
             loader.setLocation(getClass().getResource("/resources/BuildingConstruct.fxml"));
            
           
            Parent viewParent = loader.load();

            Scene cityBuildingList = new Scene(viewParent);
  
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(cityBuildingList);

            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
