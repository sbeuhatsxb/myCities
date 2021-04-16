package resources;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ObjectProvider;

import java.io.IOException;

public class LoginOverviewController {

    @FXML
    private TextField userLogin;

    @FXML
    private PasswordField userPass;

    @FXML
    private Label info;

    @FXML
    void memorize(ActionEvent event) {
        String myLogin, myPass;
        myLogin = userLogin.getText();
        myPass = userPass.getText();

        ObjectProvider objectProvider = new ObjectProvider();

        User user = objectProvider.getUserByLogin(myLogin);

        if (user != null) {
        	 String roleUser = user.getRole().getLabel();
            if (myPass.equals(user.getPassword())) {
            	if(!roleUser.equals("admin")) {
            		startUserSession(event,user);
            	}else {
            		startAdminSession(event);
            	}

            } else {
                info.setStyle("-fx-text-fill: red;");
                info.setText("Le mot de passe est incorrect");
            }
        } else {
            info.setStyle("-fx-text-fill: red;");
            info.setText("Cet utilisateur n'as pas été trouvé");
        }
    }
	/**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LoginOverviewController() {
    }

    public void startUserSession(ActionEvent event,User user) {
        try {
            FXMLLoader loader = new FXMLLoader();

             loader.setLocation(getClass().getResource("/resources/CityList.fxml"));

            Parent viewParent = loader.load();

            Scene cityBuildingList = new Scene(viewParent);
            CityList controleur =  loader.getController();
            controleur.initData(user);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(cityBuildingList);

            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            FXMLLoader loader = new FXMLLoader();
//
//            loader.setLocation(getClass().getResource("/resources/BuildingEditDetail.fxml"));
//
//            Parent viewParent = loader.load();
//
//            Scene cityBuildingList = new Scene(viewParent);
//            BuildingEditDetail controleur =  loader.getController();
//            controleur.loadData();
//            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            BuildingEditDetail truc =  loader.getController();
//
//            ObjectProvider objectProvider = new ObjectProvider();
//            Building building = objectProvider.getBuildingById(15);
//            truc.initData(building);
//            window.setScene(cityBuildingList);
//
//            window.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    private void startAdminSession(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader();

             loader.setLocation(getClass().getResource("/resources/CityList.fxml"));


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


