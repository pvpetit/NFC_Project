/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.smartcardio.CardException;

/**
 * FXML Controller class
 *
 * @author Juan Ariza
 */
public class GUI_ReadDataScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    NFC_Tag_Reader_ACR122U scanner = new NFC_Tag_Reader_ACR122U();
    ParameterTransfer PT = new ParameterTransfer();
    
    Stage stage;
    Parent root;
    
     @FXML
    private AnchorPane readDataAnchor;

    @FXML
    private Label readDataLabel;

    @FXML
    private ListView<String> NFCDataListview;

    @FXML
    private Label updatesLabel;
    
    @FXML
    private Button scanNFCTagButton;
    
    @FXML
    private Button backToMainMenuButton;
    
    // used to populate the ListView
    ObservableList NFCDatalist = FXCollections.observableArrayList();
    
    private void loadNFCDatafromScan() throws CardException {
        
        try {
            // loads data from NFC Tag
            //scanner.connect_NFC_Reader_forUID();
            String currentData = scanner.readData();
            
            // loads data into Listview
            NFCDatalist.add(currentData);
            NFCDataListview.getItems().add(currentData);
            updatesLabel.setText("Tag Scan successful");
            
        } catch (CardException here) {
            updatesLabel.setText("No card in the system. Please try again");
        }
    }
    
    @FXML
    void handleButtonAction(ActionEvent event) throws CardException {
        if (event.getSource() == scanNFCTagButton) {
            loadNFCDatafromScan();
        }
        
        else {
            try {
            // Switch screens to main menu
            stage = (Stage) backToMainMenuButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("GUI_MainMenuScreen.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            } catch (IOException loader) {
                System.out.println("Could not load next screen\n");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updatesLabel.setText("Scan your card...");
    }    
}
