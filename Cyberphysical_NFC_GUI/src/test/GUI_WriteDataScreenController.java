/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javax.smartcardio.CardException;

/**
 * FXML Controller class
 *
 * @author Juan Ariza
 */
public class GUI_WriteDataScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    NFC_Tag_Reader_ACR122U reader = new NFC_Tag_Reader_ACR122U();
    Stage stage;
    Parent root;
    
    @FXML
    private AnchorPane writeDataAnchor;
    
    @FXML
    private Label writeDataLabel;
    
    @FXML
    private Label updatesLabel;

    @FXML
    private ChoiceBox<String> dataFormatChoicebox;

    @FXML
    private TextArea userDataEntryTextarea;
    
    @FXML
    private Button writeDataToNFCButton;
    
    @FXML 
    private Button backToMainMenuButton;
    
    @FXML
    private Button clearDataButton;
    
    // loads initial parameters of the screen
    private void loadInitialFrame() {
        //dataFormatChoicebox.setItems(FXCollections.observableArrayList("Text", "URL"));
        updatesLabel.setText("Enter the data to be stored in the NFC card, then press the submit button");     
        dataFormatChoicebox.setItems(FXCollections.observableArrayList("TEXT", "URLS"));
        dataFormatChoicebox.setTooltip(new Tooltip("Select the data type"));
    }
    
    private void flushCard() {
        
        // clears value by writing spacebars on top of the data 
        final byte NULL_VAL = 32;
        byte[] test = new byte[4];
        
        for (int page = 4; page < 43; page++) {
            for (int i = 0; i < 4; i++) {
                test[i] = NULL_VAL;
            }
            reader.updateData(page, test);
        }  
        updatesLabel.setText("Card successfully cleared");
    }
    
    private void writeDataIntoNFCTag() throws CardException {
        
         // controls the length of the user NFC data input to prevent 
         // overflowing the tag. 37 pages holding 4 bytes each, 
         // with the first page reserved for classifying the record type
        final int MAX_DATA_LENGTH = 152;   
        
        if (userDataEntryTextarea.getText().isEmpty()) {
            updatesLabel.setText("No data has been entered. Please enter data, or press Clear button to clear NFC card");
        }
        
        else if (userDataEntryTextarea.getText().length() > MAX_DATA_LENGTH) {
            updatesLabel.setText("The data is too large for the tag. Please keep entries below " + MAX_DATA_LENGTH + " characters");
        }
        
        else {

            // collect user data from text area 
            String userData = userDataEntryTextarea.getText();
            // send into stringbuilder 
            StringBuilder transformer = new StringBuilder();
            transformer.append(userData);
            
            // maintains the NFC tag page 
            int page = 4;
            int counter = 0;
            
            // adds filler space in string to ensure 4-byte package deliveries
            while (transformer.length() % 4 != 0) {
                transformer.append(" ");
            }
            
            System.out.println(transformer);
            
            /* the data is packages of 4 bytes per page (4 characters), so any 
            / inputs that are smaller than 4 characters per page must be filled 
            / with a spacebar*/
            byte[] test = new byte[4];
            
            while (counter != transformer.length()) {
            
                for (int i = 0; i < 4; i++) {
                    test[i] = (byte) transformer.charAt(counter);
                    counter++;
                }
                
                reader.updateData(page, test);
                page++;
            }
        }
        
        updatesLabel.setText("Data write successful");
    }
    
    @FXML
    void handleButtonAction(ActionEvent event) throws CardException {
         if (event.getSource() == writeDataToNFCButton) {
            writeDataIntoNFCTag();
        }
         
        else if (event.getSource() == clearDataButton) {
            flushCard();
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
        loadInitialFrame();
    }        
}
