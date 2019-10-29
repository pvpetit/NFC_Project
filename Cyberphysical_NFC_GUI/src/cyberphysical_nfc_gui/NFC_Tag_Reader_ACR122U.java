package cyberphysical_nfc_gui;

/*
 * Uses the JavaX Smartcardio package to communicate NFC device to program
 */

import java.util.List;
import java.math.BigInteger;
import javax.smartcardio.*;

public class NFC_Tag_Reader_ACR122U {
    
    // Default Constructor
    public void NFC_Tag_Reader_ACR122U() {
    
    }
    
    // converts the raw binary data to hexadecimal 
    public static String bin2hex(byte[] data) {
	    return String.format("%0" + (data.length * 2) + "X", new BigInteger(1,data));
    }
    
    public static void main(String args[]) {
        NFC_Tag_Reader_ACR122U test = new NFC_Tag_Reader_ACR122U();
        test.connect_NFC_Reader_forUID();
    }
    
    // handles the connection to the NFC Reader device and related exceptions
    public String connect_NFC_Reader_forUID() {
         
        // returns the necessary tag ID
        String tagID = null;
        
        try {
            TerminalFactory factory = TerminalFactory.getDefault();
            List<CardTerminal> terminals = factory.terminals().list();
            
            CardTerminal first = terminals.get(0);
            
            System.out.println("Please scan your card...\n");
            while (!first.isCardPresent()) {
                first.waitForCardPresent(0);
            }
            
            if (first.isCardPresent()) {
                Card card = first.connect("*");
                // TODO: replace with GUI component
                
                //COMMENT-OUT FOR TESTING
                //System.out.println("Current card: " + card); 
            
                CardChannel channel = card.getBasicChannel();
            
                ResponseAPDU response = channel.transmit(new CommandAPDU( new byte[] { (byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00 }));
                
                //COMMENT-OUT FOR TESTING
                //System.out.println("Response type: " + response.toString());
            
                if (response.getSW1() == 0x63 && response.getSW2() == 0x00) {
                     System.out.println("Failed Tag Response\n"); 
                }
            
                //COMMENT-OUT FOR TESTING
                //System.out.println("Tag UID: " + bin2hex(response.getData()));
                tagID = bin2hex(response.getData());
            }
        } catch (CardException ex) {
            System.out.println(ex);
        }
        
         return tagID;
     }
    
    public void writeData(Card card, byte block, byte[] data)  throws CardException {
        
    
    
    }
    
    
// ends class
}
