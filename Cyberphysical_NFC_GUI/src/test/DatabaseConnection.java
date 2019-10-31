package TEST;

/*
 * Connects to the built-in Derby database localhost 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
/**
 *
 * @author Juan Ariza
 */
public class DatabaseConnection {
    
    // Database URL and connection tokens
    
    //private final static String embedSetup = "org.apache.derby.jdbc.EmbeddedDriver";
    private final static String databaseURL = "jdbc:sqlite:EmployeeRegistry.db";
    private static Connection conn = null;
    private static Statement stmt = null;
    
    // statement used to check if the username and password coincide to the given records
    private static String loginSQL = "SELECT cardID FROM Employee WHERE username=? AND password=?" ;
    
    // Default Constructor
    public void DatabaseConnection() {
        createConnection();
        findUIDfromLogin();
    }
    
    public static void main(String[] args) {
        DatabaseConnection test = new DatabaseConnection();
        test.DatabaseConnection();
    }
    
    public void createConnection() {
        try {
            // creates connection
            conn = DriverManager.getConnection(databaseURL);
            System.out.println("DATABASE connected");
            
        } catch(Exception ex) {
            System.out.println("Error present: " + ex);
        }
    }
    
    public String[] collectUserDetails() {
        String[] loginDetails = new String[2];
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your username: ");
        loginDetails[0] = input.next();
        input.reset();
        
        System.out.println("Enter your password: ");
        loginDetails[1] = input.next();
       
        return loginDetails;
    }
    
    // Overloaded method designed for GUI
    public String[] collectUserDetails(String username, String password) {
        String[] loginDetails = new String[2];
         
        loginDetails[0] = username;
        loginDetails[1] = password;
        
        return loginDetails;
    }
    
    public String findUIDfromLogin() {
        String[] details = collectUserDetails();
        String username = details[0];
        String password = details[1];
        String userTagID = null;
        
        try {
            PreparedStatement preState = conn.prepareStatement(loginSQL);
            preState.setString(1, username);
            preState.setString(2, password);
            
            ResultSet rs = preState.executeQuery();
            
            while (rs.next()) {
                /*summon the corresponding Tag ID set to the User's 
                * account (username and password) from the database*/
                userTagID = rs.getString("cardID");
            }
            System.out.println(userTagID);
    
        } catch (SQLException es) {
            System.out.println("Error: " + es);
        }
        
        return userTagID;
    }
    
    // Overloaded method for use in GUI
    public String findUIDfromLogin(String[] userInfo) {
        String username = userInfo[0];
        String password = userInfo[1];
        String userTagID = null;
        
        try {
            PreparedStatement preState = conn.prepareStatement(loginSQL);
            preState.setString(1, username);
            preState.setString(2, password);
            
            ResultSet rs = preState.executeQuery();
            
            while (rs.next()) {
                /*summon the corresponding Tag ID set to the User's 
                * account (username and password) from the database*/
                userTagID = rs.getString("cardid");
            }
    
        } catch (SQLException es) {
            System.out.println("Error: " + es);
        }
        
        System.out.println(userTagID);
        return userTagID;
    }
    
    
    public void confirmEmployeeData() {
        /* Employee data fields: 
        *  CardID: the NFC tag's Unique ID (14 characters)
        *  First Name, Last Name, Age
        */
        
        // stores results of the scanned tag ID
        String compareToIncomingTagID;
        
        // stores the database call for record containing the NFC tag
        String databaseTagID = findUIDfromLogin();
        //System.out.println(databaseTagID);
        
        /*NFC_Tag_Reader_ACR122U testing = new NFC_Tag_Reader_ACR122U();
        
        compareToIncomingTagID = testing.connect_NFC_Reader_forUID(); 
        
        if (compareToIncomingTagID.equals(databaseTagID)) {
            // checks if database tag ID is the same as the physical tag ID
            System.out.println("LOGIN SUCCESSFUL");
        }
        else {
            System.out.println("The Tag data does not match the User. Please try again/n");
        } */
    }
    // method end
}
