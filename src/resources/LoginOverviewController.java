package resources;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ObjectProvider;
import sample.Main;

import java.io.IOException;

public class LoginOverviewController {

    
	// Reference to the main application.
    private Main main;
     private  User currentUser;
     

    @FXML
    private TextField userLogin;

    @FXML
    private PasswordField userPass;

    @FXML
    private Button access;

    @FXML
    private TextField info;

    @FXML
    void memorize(ActionEvent event) {
        String myLogin, myPass;
        myLogin = userLogin.getText();
        myPass = userPass.getText();

        ObjectProvider objectProvider = new ObjectProvider();

        User user = objectProvider.getUserByLogin(myLogin);
       
        
        
        if (user != null) {
            //TODO j'ai un utilisatuer qui a ce login
        	 String roleUser = user.getRole().getLabel();

            if (myPass.equals(user.getPassword())) {
                //TODO Alors je suis conencté
            	
            	if(!roleUser.equals("admin")) {
            		startUserSession(event,user);
            	}else {
            		startAdminSession(event);
            	}
                
                
               

            } else {
                //TODO ALERT mot de passe incorrect
                info.setText("le mot de passe incorrect");
            }
        } else {
            //TODO ALERT Utilisateur non trouv�
            info.setText("ce utilisateur n'as pas �t� trouv�");
        }


    }
   
  
    
   

    





	/**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LoginOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.


        // Listen for selection changes and show the person details when changed.

    }
//    @FXML
//    private void initialize() {
//        // Initialize the person table with the two columns.
//        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
//    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param main
     */
    public void setMainApp(Main main) {
        this.main = main;

        // Add observable list data to the table

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


