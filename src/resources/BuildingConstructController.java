package resources;

import java.awt.TextArea;

import entities.Building;
import entities.City;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.DbFeeder;
import model.ObjectProvider;

public class BiildingConstructController {
	private Building building  = new Building();
	private ObjectProvider op = new ObjectProvider();

	@FXML
    private TextArea fxDesc;

    @FXML
    private ChoiceBox<?> fxCity;

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
	    
	  
	    
	   

}
