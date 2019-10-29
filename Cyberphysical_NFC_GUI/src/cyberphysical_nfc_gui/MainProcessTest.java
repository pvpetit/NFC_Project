/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cyberphysical_nfc_gui;

/**
 *
 * @author Juan Ariza
 */
public class MainProcessTest {
    
    public static void main(String args[]) {
       // NFC_Tag_Reader_ACR122U reader = new NFC_Tag_Reader_ACR122U();
        DatabaseConnection records = new DatabaseConnection();
        //NFC_Tag_Reader_ACR122U reader =  new NFC_Tag_Reader_ACR122U();
        
        // connect to database
        records.DatabaseConnection();
        records.confirmEmployeeData();
    }
}
