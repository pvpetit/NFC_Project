/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author Juan Ariza
 */
public class ScreenManager {

    private static Stage mainStage; // This probably doesn't need to be static
    private FXMLLoader fxmlLoader;
    private static ScreenManager instance;

    private ScreenManager() {
        // Intentionally blank.
    }
    
    public enum screenSelection {
    //Enum used to store the relative path of the screens.
    // Cleans code presentation.

        MAIN_LOGIN_USERPASS {
            @Override
            public String toString() {
                return "FXMLDocument.fxml";
            }
        },

        MAIN_LOGIN_NFC{
            @Override
            public String toString() {
                return "GUI_LoginNFCScreen.fxml";
            }
        },

        MAIN_MENU{
            @Override
            public String toString() {
                return "GUI_MainMenuScreen.fxml";
            }
        },

        READ_DATA{
            @Override
            public String toString() {
                return "GUI_ReadDataScreen.fxml";
            }
        },
    
        WRITE_DATA{
            @Override
            public String toString() {
                return "GUI_WriteDataScreen.fxml";
            }
        }
    }

    /**
     * Returns a reference to the singleton.
     *
     * Be sure that initialize has been called before attempting to use the object, else a NPE will
     * be thrown. The best place to call initialize is at the very starting point of the app.
     *
     * @return A reference to the singleton instance.
     */
    public static ScreenManager getInstance() {
        if (instance == null ) {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**
     * Before the object can be used, it must be provided a reference to the main stage.
     *
     * @param mainStage The main stage of the application.
     */
    public void initialize(Stage mainStage) {
        this.mainStage = mainStage;
    }

    /**
     * @param fxmlPath The path to the FXML file of the screen to switch to.
     *
     * @return The FXML controller of the new scene.
     */
    public Object switchToScreen(String fxmlPath) {
        Parent newScreenRoot;

        try {
            URL pathToFxml = getClass().getResource(fxmlPath);
            fxmlLoader = new FXMLLoader(pathToFxml);
            newScreenRoot = fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to load FXML", e);
        }

        mainStage.getScene().setRoot(newScreenRoot);
        return fxmlLoader.getController();
    }

    /**
     * @return Whether the singleton is properly initialized and ready for use.
     */
    public boolean isInitialized() {
        return mainStage == null;
    }
}
