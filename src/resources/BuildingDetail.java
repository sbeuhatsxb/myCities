package resources;

import entities.Building;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BuildingDetail {

    @FXML
    private ImageView fxImage;
    @FXML
    private TextField fxYear;
    @FXML
    private TextField fxCity;
    @FXML
    private TextField fxCountry;
    @FXML
    private TextField fxArchitect;
    @FXML
    private TextField fxStyle;
    @FXML
    private TextField fxType;
    @FXML
    private TextField fxMaterial;
    @FXML
    private TextField fxRoofType;
    @FXML
    private TextField fxFrame;
    @FXML
    private TextField fxName;
    @FXML
    private TextArea fxDescription;

    public void initData(Building building, User user){

        String yearText = String.valueOf(building.getYear());
        fxArchitect.setText(building.getArchitect().getLabel());
        fxFrame.setText(building.getFrame().getLabel());
        fxCity.setText(building.getCity().getLabel());
        fxCountry.setText(building.getCity().getCountry().getLabel());
        fxName.setText(building.getName());
        fxRoofType.setText(building.getRoofType().getLabel());
        fxYear.setText(yearText);
        fxMaterial.setText(building.getMaterial().getLabel());
        fxStyle.setText(building.getStyle().getLabel());
        fxFrame.setText(building.getFrame().getLabel());
        fxType.setText(building.getType().getLabel());
        Image image = new Image("/resources/img/"+building.getImage());
        fxImage.setImage(image);
        fxDescription.setText(building.getDescription());
    }
}
