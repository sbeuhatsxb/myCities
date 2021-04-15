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
	ObservableList list = FXCollections.observableArrayList();
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
    	
    	/*building.setName(fxName.getText());
    	building.setDescription(fxDesc.getText());
    	building.setYear(Integer.parseInt(fxYear.getText()));
    	building.setWindows(Integer.parseInt(fxWindows.getText()));
    	building.setCity(op.getCityByName(fxName.getText()));
    	building.setArchitect(null);
    	building.setMaterial(null);
    	building.setType(null);
    	building.setRoofType(null);
    	building.setFrame(null);
    	building.setStyle(null);
    	DbFeeder dbfeeder = new DbFeeder(); 
    	dbfeeder.addNewBuilding(building);*/

    }
    
    @FXML
    public void initialize() {
        loadData();
       
    }
    
    private void loadData() {
    	 list.removeAll(list);

         List<Object> citiesGetter = objectProvider.getAllBuildings();
         HashSet<String> cities = new HashSet<>();
         String cityName;
         for (int i = 0; i < citiesGetter.size(); i++) {
             Building city = (Building) citiesGetter.get(i);
             cityName = city.getCity().getLabel();
             cities.add(cityName);
         }

         list.addAll(cities);
         fxCity.getItems().addAll(list);
    }
	    
	  
	    
	   

}
