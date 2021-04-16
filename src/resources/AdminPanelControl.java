package resources;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPanelControl {

public class PleaseProvideControllerClassName {

    @FXML
    private Button fxAjoutBat;

    @FXML
    private Button fxModifBat;

}

	public void ToModify(ActionEvent event ) {
		try {
            FXMLLoader loader = new FXMLLoader();

             loader.setLocation(getClass().getResource("/resources/CityOverview.fxml"));


            Parent viewParent = loader.load();

            Scene modifyScene = new Scene(viewParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(modifyScene);

            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

	public void ToCreate(ActionEvent event) {
		try {
            FXMLLoader loader = new FXMLLoader();

             loader.setLocation(getClass().getResource("/resources/BuildingConstruct.fxml"));


            Parent viewParent = loader.load();

            Scene createScene = new Scene(viewParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(createScene);

            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	}





