/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Juan Ariza
 */
public class GUI_MainMenuScreenController implements Initializable {
    
    ParameterTransfer PT = new ParameterTransfer();
    // used for screen switching
    Stage stage;
    Parent root;
    
    @FXML
    private AnchorPane mainMenuAnchor;

    @FXML
    private Button readDataButton;

    @FXML
    private Button writeDataButton;
    
    @FXML
    private Button returnToUserPassLoginButton;
    
    @FXML 
    private Button adminOptionsButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label readNFCLabel;

    @FXML
    private Label writeNFCLabel;
    
    @FXML
    private Label adminOptionsLabel;
    
    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        
        try {
           
            // loads Read Data screen
            if (event.getSource() == readDataButton) {
                stage = (Stage) readDataButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("GUI_ReadDataScreen.fxml"));            
            } 
            // loads Write Data screen
            else if (event.getSource() == writeDataButton) {
                stage = (Stage) writeDataButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("GUI_WriteDataScreen.fxml"));   
            }
            // loads Admin options if the user logins with 2-factor
            
            /*
            else if (event.getSource() == adminOptionsLabel) {
                // create and load Admin screen
            } */
            
            // logs out back to main menu, signs off
            else if (event.getSource() == returnToUserPassLoginButton) {
                stage = (Stage) returnToUserPassLoginButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));  
            }
        
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            } catch (IOException load) {
                System.out.println("Could not load next screen\n");
            }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        welcomeLabel.setTextAlignment(TextAlignment.CENTER);
        
        if (PT.getIsAdmin() == false) {
            adminOptionsButton.setDisable(true);
            //adminOptionsButton.setTooltip(new Tooltip("Please log in with an Admin account to access this feature"));
        }
        
        else {
            adminOptionsButton.setDisable(false);
        }
        
        welcomeLabel.setText("Welcome, " + PT.getUserFirstName() + " " + PT.getUserLastName());
        
    }    
    
}
