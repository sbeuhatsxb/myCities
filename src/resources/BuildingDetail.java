package resources;

import entities.Building;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.DbFeeder;
import model.ObjectProvider;

import java.io.IOException;

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
    @FXML
    private Button fxSeeFav;
    @FXML
    private Button fxAddFav;
    @FXML
    private Label idAddFavText;
    User user;
    Building building;
    DbFeeder dbFeeder = new DbFeeder();
    ObjectProvider objectProvider = new ObjectProvider();

    public void initData(Building currentBuilding, User currentUser){

        String yearText = "";
        building = currentBuilding;
        user = currentUser;
        if(building.getYear() != 0){
            yearText = String.valueOf(building.getYear());
            fxYear.setText(yearText);
        }
        if(building.getArchitect() != null){
            fxArchitect.setText(building.getArchitect().getLabel());
        }
        if (building.getFrame() != null){
            fxFrame.setText(building.getFrame().getLabel());
        }
        if (building.getCity().getLabel() != null) {
            fxCity.setText(building.getCity().getLabel());
        }
//        if (building.getCity().getCountry().getLabel() != null) {
//            fxCountry.setText(building.getCity().getCountry().getLabel());
//        }
        if(building.getName() != null){
            fxName.setText(building.getName());
        }
        if(building.getRoofType() != null){
            fxRoofType.setText(building.getRoofType().getLabel());
        }
        if(building.getMaterial() != null){
            fxMaterial.setText(building.getMaterial().getLabel());
        }
        if(building.getStyle() != null) {
            fxStyle.setText(building.getStyle().getLabel());
        }
        if(building.getFrame() != null) {
            fxFrame.setText(building.getFrame().getLabel());
        }
        if(building.getType() != null) {
            fxType.setText(building.getType().getLabel());
        }
        if(building.getImage() != null) {
            Image image = new Image("/resources/img/" + building.getImage());
            fxImage.setImage(image);
        }
        if(building.getDescription() != null){
            fxDescription.setText(building.getDescription());
        }

        if(objectProvider.checkFavList(building, user)){
            fxAddFav.setText("Retirer des favoris ?");
            fxAddFav.setStyle("-fx-text-fill: red;");
        } else {
            fxAddFav.setText("Ajouter aux favoris");
            fxAddFav.setStyle("-fx-text-fill: green;");
        }
    }


    public void onClickedSeeFav(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/FavList.fxml"));
            Parent viewParent = loader.load();

            Scene favList = new Scene(viewParent);

            //Access controller
            FavList controller = loader.getController();
            controller.initData(user);

            Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(favList);

            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickedAddToFav(javafx.event.ActionEvent actionEvent) {
        if(objectProvider.checkFavList(building, user)){
            dbFeeder.removeFromFavlist(building, user);
            fxAddFav.setText("Ajouter aux favoris");
            fxAddFav.setStyle("-fx-text-fill: green;");
        } else {
            if(dbFeeder.addToFavlist(building, user) == true){
                idAddFavText.setText("Bâtiment ajouté aux favoris");
                idAddFavText.setStyle("-fx-font-style: italic");
                fxAddFav.setText("Retirer des favoris ?");
                fxAddFav.setStyle("-fx-text-fill: red;");
            }
        }
    }

    public void onClickReturnHome(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/resources/CityList.fxml"));

            Parent viewParent = loader.load();

            Scene cityBuildingList = new Scene(viewParent);
            CityList controleur =  loader.getController();
            controleur.initData(user);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(cityBuildingList);

            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
