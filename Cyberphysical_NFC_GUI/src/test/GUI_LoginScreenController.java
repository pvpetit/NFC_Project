/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Juan Ariza
 */
public class GUI_LoginScreenController implements Initializable {
    
    @FXML
    private AnchorPane loginUserPassAnchor;
    
    @FXML
    private Label loginStatus;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField usernameEnter;

    @FXML
    private PasswordField passwordEnter;

    @FXML
    private Button loginButton;
    
    @FXML
    private Button guestButton;
    
    // used for screen switching
    Stage stage;
    Parent root;

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == guestButton) {
            /* enter as a Guest into the system. Read/write procedures are 
               available for use, but the Admin features are locked */
            try {
                // switch to main menu screen, Admin procedures locked
                ParameterTransfer PT = new ParameterTransfer();
                PT.setUserFirstName("Guest");
                PT.setUserLastName("User");
                
                stage = (Stage) guestButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("GUI_MainMenuScreen.fxml"));
                
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                
            } catch (IOException here) {
                System.out.println("Cannot load screen");
            }
        } else {
            checkUserInput(usernameEnter, passwordEnter);
        }
    }
    
    // field used for checking database results 
    DatabaseConnection loginConnect = new DatabaseConnection();
    
    /* Method to check username and password called by button click action.
     * Checks for the following errors in username and/or password
     * - if fields are empty when user clicks login
     * - if fields are in the wrong format (not 8 characters)
     * - finally, if the given fields coincide with the database's credentials
     */
    void checkUserInput(TextField username, PasswordField password) {
        
        loginConnect.createConnection();    // connects class to database info
        ParameterTransfer PT = new ParameterTransfer();     // sends info across screens
        
        String tempLog = loginConnect.findUIDfromLogin(username.getText(), password.getText());
        
        // checks if fields are empty
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            loginStatus.setText("The username or password is empty. Please try again");
            loginStatus.setVisible(true);
        }
       
        // checks if given 8-character username/password matches database records
        else if (tempLog == null) {
            loginStatus.setText("The current credentials do not match our records. Please try again");
            loginStatus.setVisible(true);
        }
      
        // once correct input is given, pass necessary parameters over and move on to NFC tag login screen
        else {
            loginStatus.setText("Success. Logging in...");
            loginStatus.setVisible(true);
            PT.setIsLoginCorrect(true);
            PT.setTagUIDfromLogin(tempLog);
            PT.setIsAdmin(true);
            
            try {
            // Switch screens to NFC Scan login 
            stage = (Stage) loginButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GUI_LoginNFCScreen.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            } catch (IOException load) {
                System.out.println("Could not load next screen\n");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loginStatus.setVisible(false);
    }    
    
}
