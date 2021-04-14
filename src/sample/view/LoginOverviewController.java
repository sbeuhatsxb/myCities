package sample.view;

import javafx.fxml.FXML;
import sample.Main;
import entities.User;
import model.ObjectProvider;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginOverviewController {
	
	// Reference to the main application.
    private Main main;
private TextField log,pass;
	
	
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
	String myLogin,myPass;
	myLogin= userLogin.getText();
	myPass=userPass.getText();
	
	ObjectProvider objectProvider = new ObjectProvider();
	
	User user = objectProvider.getUserByLogin(myLogin);
	if(user != null){
	    //TODO j'ai un utilisatuer qui a ce login
		
			
		
		
	    if(myPass == user.getPassword()){
	        //TODO Alors je suis conencté
	    } else {
	        //TODO ALERT mot de passe incorrect
	    	info.setText("le mot de passe incorrect");
	    }
	} else {
	    //TODO ALERT Utilisateur non trouvé
		info.setText("ce utilisateur n'as pas été trouvé");
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


}


