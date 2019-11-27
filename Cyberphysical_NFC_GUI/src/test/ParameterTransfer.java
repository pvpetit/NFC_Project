/*
 * The following class collects the necessary parameters from screen to screen
 * to reduce over-writing methods in each screen controller class
 */
package test;

import javax.smartcardio.Card;


/**
 *
 * @author Juan Ariza
 */
public class ParameterTransfer {

    // variables used for username/password login and NFC login, respectively
    private static boolean isLoginCorrect = false;
    private static boolean isTagCorrect = false;
    private static String tagUIDfromLogin = null;
    private static String tagUIDfromNFC = null;
    
    // variables collected from successful login procedures
    private static String userFirstName = null;
    private static String userLastName = null;
    private static boolean Admin;

    private Card currentCard = null;
        
    public boolean getIsLoginCorrect() {
        return isLoginCorrect;
    }

    public boolean getIsTagCorrect() {
        return isTagCorrect;
    }
    
    // setters should be called in screen controller
    public void setIsLoginCorrect(boolean isLoginCorrect) {
        ParameterTransfer.isLoginCorrect = isLoginCorrect;
    }

    public void setIsTagCorrect(boolean isTagCorrect) {
        ParameterTransfer.isTagCorrect = isTagCorrect;
    }
    
    public String getTagUIDfromLogin() {
        return tagUIDfromLogin;
    }

    public String getTagUIDfromNFC() {
        return tagUIDfromNFC;
    }
    
    public void setTagUIDfromLogin(String tagUIDfromLogin) {
        ParameterTransfer.tagUIDfromLogin = tagUIDfromLogin;
    }

    public void setTagUIDfromNFC(String tagUIDfromNFC) {
        ParameterTransfer.tagUIDfromNFC = tagUIDfromNFC;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        ParameterTransfer.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        ParameterTransfer.userLastName = userLastName;
    }

    public Card getCurrentCard () {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }
    
    public boolean getIsAdmin() {
        return Admin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.Admin = isAdmin;
    }
    
    
 
}
