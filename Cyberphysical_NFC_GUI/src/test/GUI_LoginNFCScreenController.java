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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.smartcardio.CardException;

/**
 * FXML Controller class
 *
 * @author Juan Ariza
 */
public class GUI_LoginNFCScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    // used to access Reader methods
    NFC_Tag_Reader_ACR122U reader = new NFC_Tag_Reader_ACR122U();
    
    // used to synchronize login features
    ParameterTransfer PT = new ParameterTransfer();
    
    @FXML
    private AnchorPane loginNFCScanAnchor;
    
    @FXML
    private Label tagScanTopMessage;

    @FXML
    private Label tagScanBottomMessage;
    
    @FXML
    private Button loginNFCButton;
    
    @FXML
    private Button returnToLoginUserPassButton;
    
    // used for screen switching
    Stage stage;
    Parent root;
    
    void loadInitialFrame() {
        tagScanTopMessage.setVisible(true);
        tagScanTopMessage.setText("Please Scan your NFC Tag, press the button, then remove the tag...");
        
        tagScanBottomMessage.setVisible(true);
        tagScanBottomMessage.setText("Current Status: Unscanned");
    }
        
    void checkTagScanStatus() {
        
        do {    
          
            PT.setTagUIDfromNFC(reader.connect_NFC_Reader_forUID());
            
            if (PT.getTagUIDfromNFC() == null || !PT.getTagUIDfromNFC().equals(PT.getTagUIDfromLogin())) {
                tagScanTopMessage.setText("Tag ID Failed. Failed User NFC tag");
                tagScanBottomMessage.setText("Please Scan Again");
            } 
        } while (PT.getTagUIDfromNFC() == null || !PT.getTagUIDfromNFC().equals(PT.getTagUIDfromLogin()));
        
        tagScanTopMessage.setText("Tag ID Successful");
        tagScanBottomMessage.setText("Logging you in...");
        
        try {
            // Switch screens to Main Menu
            stage = (Stage) loginNFCButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GUI_MainMenuScreen.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            } catch (IOException load) {
                System.out.println("Could not load next screen\n");
            }
    }
    
    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == returnToLoginUserPassButton) {
             try {
            // Switch screens to username/password login
            stage = (Stage) returnToLoginUserPassButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            } catch (IOException load) {
                System.out.println("Could not load next screen\n");
            }
        
        } else {
            checkTagScanStatus();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadInitialFrame();
        //checkTagScanStatus();
    }    
    
}
