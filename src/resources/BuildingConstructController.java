package resources;



import java.util.HashSet;
import java.util.List;

import entities.Building;
import entities.City;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    void insertion(ActionEvent event) {
    	fxInsert.setVisible(false);
    	
    	building.setName(fxName.getText());
    	building.setDescription(fxDesc.getText());
    	building.setYear(Integer.parseInt(fxYear.getText()));
    	building.setWindows(Integer.parseInt(fxWindows.getText()));
    	building.setCity(objectProvider.getCityByName(fxCity.getValue()));
    	building.setArchitect(null);
    	building.setMaterial(null);
    	building.setType(null);
    	building.setRoofType(null);
    	building.setFrame(null);
    	building.setStyle(null);
    	DbFeeder dbfeeder = new DbFeeder(); 
    	dbfeeder.addNewBuilding(building);

    }
    
    @FXML
    public void initialize() {
        loadData();
       
    }
    
    private void loadData() {
    	 cityList.removeAll(cityList);
    	 
    	 

         List<Object> citiesGetter = objectProvider.getAllBuildings();
         HashSet<String> cities = new HashSet<>();
         String cityName;
         for (int i = 0; i < citiesGetter.size(); i++) {
             Building city = (Building) citiesGetter.get(i);
             cityName = city.getCity().getLabel();
             cities.add(cityName);
         }

         cityList.addAll(cities);
         fxCity.getItems().addAll(cityList);
         
         
         	
         architectList.removeAll(architectList);
    	
         List<Object> architectGetter = objectProvider.getAllBuildings();
         HashSet<String> architects = new HashSet<>();
         String architectName;
         for (int i = 0; i < architectGetter.size(); i++) {
             Building architect = (Building) architectGetter.get(i);
             architectName = architect.getArchitect().getLabel();
             architects.add(architectName);
         }

         architectList.addAll(architects);
         fxArchitect.getItems().addAll(architectList);
         
         typeList.removeAll(typeList);
     	
         List<Object> typeGetter = objectProvider.getAllBuildings();
         HashSet<String> types = new HashSet<>();
         String typeName;
         for (int i = 0; i < typeGetter.size(); i++) {
             Building type = (Building) typeGetter.get(i);
             typeName = type.getType().getLabel();
             types.add(typeName);
         }

         typeList.addAll(types);
         fxType.getItems().addAll(typeList);
         
         materialList.removeAll(materialList);
      	
         List<Object> materialGetter = objectProvider.getAllBuildings();
         HashSet<String> materials = new HashSet<>();
         String materialName;
         for (int i = 0; i < materialGetter.size(); i++) {
             Building material = (Building) materialGetter.get(i);
             materialName = material.getMaterial().getLabel();
             materials.add(materialName);
         }

         materialList.addAll(materials);
         fxMaterial.getItems().addAll(materialList);
      /*   
         toitList.removeAll(toitList);
        	
         List<Object>toitGetter = objectProvider.getAllBuildings();
         HashSet<String> toits = new HashSet<>();
         String toitName;
         for (int i = 0; i < toitGetter.size(); i++) {
             Building toit = (Building) toitGetter.get(i);
             toitName = toit.getRoofType().getLabel();
             toits.add(toitName);
         }

         toitList.addAll(toits);
         fxToit.getItems().addAll(toitList);
         
     */
         
         charpenteList.removeAll(charpenteList);
        	
         List<Object> frameGetter = objectProvider.getAllBuildings();
         HashSet<String> charpentes = new HashSet<>();
         String charpenteName;
         for (int i = 0; i < frameGetter.size(); i++) {
             Building charpente = (Building) frameGetter.get(i);
             charpenteName = charpente.getFrame().getLabel();
             charpentes.add(charpenteName);
         }

         charpenteList.addAll(charpentes);
         fxFrame.getItems().addAll(charpenteList);
         
         
         
     //
         
        styleList.removeAll(styleList);
        	
         List<Object> styleGetter = objectProvider.getAllBuildings();
         HashSet<String> styles = new HashSet<>();
         String styleName;
         for (int i = 0; i < styleGetter.size(); i++) {
             Building style = (Building) styleGetter.get(i);
             styleName = style.getStyle().getLabel();
             styles.add(styleName);
         }

         styleList.addAll(styles);
         fxStyle.getItems().addAll(styleList);
    }
	    
    
    
	  
	    
	   

}
