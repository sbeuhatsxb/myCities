package resources;

import java.awt.TextArea;

import entities.Building;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.DbFeeder;

public class BiildingConstructController {
	private Building building  = new Building();;

    @FXML
    private TextField fxYear;

    @FXML
    private TextField fxWindows;

    @FXML
    private TextField fxCity;

    @FXML
    private TextField fxArchitect;

    @FXML
    private TextField fxType;

    @FXML
    private TextField fxMaterial;

    @FXML
    private TextField fxRoof;

    @FXML
    private TextField fxFrame;

    @FXML
    private TextField fxStyle;

    @FXML
    private TextField fxName;

    @FXML
    private TextArea fxDesc;

    @FXML
    private ImageView fxImg;

    @FXML
    private Button fxInsert;
	    
	    
	    public void insertion() {
	    	
	    	
	    	building.setName(fxName.getText());
	    	
	    	DbFeeder dbfeeder = new DbFeeder(); 
	    	dbfeeder.addNewBuilding(building);
	    }
	    

}
