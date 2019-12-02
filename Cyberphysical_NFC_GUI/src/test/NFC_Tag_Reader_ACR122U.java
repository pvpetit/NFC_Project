package test;

/*
 * Uses the JavaX Smartcardio package to communicate NFC device to program
 */

import java.util.List;
import java.math.BigInteger;
import java.util.Arrays;
import javax.smartcardio.*;

public class NFC_Tag_Reader_ACR122U {
    
    // Default Constructor
    public void NFC_Tag_Reader_ACR122U() {
        
    }
    
    ParameterTransfer PT = new ParameterTransfer();
    
    // converts the raw binary data to hexadecimal 
    public String bin2hex(byte[] data) {
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
            
            while (!first.isCardPresent()) {
                first.waitForCardPresent(0);
            }
            
            if (first.isCardPresent()) {
                Card card = first.connect("*");
                // TODO: replace with GUI component
                
                //COMMENT-OUT FOR TESTING
                //System.out.println("Current card: " + card); 
                //PT.setCurrentCard(card);
            
                CardChannel channel = card.getBasicChannel();
                
                CommandAPDU readUIDcommand = new CommandAPDU(new byte[]{(byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00});
            
                ResponseAPDU response = channel.transmit(readUIDcommand);
                
                //COMMENT-OUT FOR TESTING
                //System.out.println("Response type: " + response.toString());
            
                if (response.getSW1() == 0x63 && response.getSW2() == 0x00) {
                     System.out.println("Failed Tag Response\n"); 
                }
            
                //COMMENT-OUT FOR TESTING
                System.out.println("Tag UID: " + bin2hex(response.getData()));
                tagID = bin2hex(response.getData());
            }
        } catch (CardException ex) {
            System.out.println(ex);
        }
        
         return tagID;
     }
        
    public boolean updateData(int page, byte[] data) {
        
		if (page < 4 || page > 42) {
			// outside of user memory
			return false;
		}

                try {
                    TerminalFactory factory = TerminalFactory.getDefault();
                    List<CardTerminal> terminals = factory.terminals().list();
            
                    CardTerminal first = terminals.get(0);
            
                    while (!first.isCardPresent()) {
                        first.waitForCardPresent(0);
                    }
            
                    if (first.isCardPresent()) {
                        Card card = first.connect("*");
                
                        CardChannel channel = card.getBasicChannel();
                
                        byte[] apdu = new byte[9];
                        apdu[0] = (byte) 0xFF;
                        apdu[1] = (byte) 0xD6;
                        apdu[2] = 0x00;
                        apdu[3] = (byte) page;
                        apdu[4] = 0x04;
                
                        System.arraycopy(data, 0, apdu, 5, 4);
                
                        CommandAPDU command = new CommandAPDU(apdu);
                        ResponseAPDU response = channel.transmit(command);
		
                        if (response.getSW() != (0x90 << 8 | 0x00)) {
                            return false;
                        }

                        return true;
                    }  
                } catch (CardException ex) {
                    System.out.println("Cannot write to card");
                }
         return true;  
    }
    
    public String readData() throws CardException {
        
       String tagData;
       StringBuilder tempCollect = new StringBuilder();
       
       try {
           
            TerminalFactory factory = TerminalFactory.getDefault();
            List<CardTerminal> terminals = factory.terminals().list();
            
            CardTerminal first = terminals.get(0);
            
            while (!first.isCardPresent()) {
                first.waitForCardPresent(0);
            }
            
            if (first.isCardPresent()) {
                Card card = first.connect("*");
               
                CardChannel channel = card.getBasicChannel();
                
                for (int page = 4; page < 43; page++) {
              
                    CommandAPDU readDatacommand = new CommandAPDU(new byte[]{(byte) 0xFF, (byte) 0xB0, (byte) 0x00, (byte) page, (byte) 0x04});
            
                    ResponseAPDU response = channel.transmit(readDatacommand);
                    validateResponse(response);
                
                    if (response.getSW1() == 0x63 && response.getSW2() == 0x00) {
                        System.out.println("Failed Tag Response\n"); 
                    }
                
                    tagData = new String(response.getData());
                    tempCollect.append(tagData);
                    //System.out.println(tagData);
                
                }
                
                //COMMENT-OUT FOR TESTING
                //System.out.println("Tag UID: " + bin2hex(response.getData()));
            }
            
        } catch (CardException ex) {
            System.out.println(ex);
        }
       return tempCollect.toString();
    }

    private static void validateResponse(ResponseAPDU response) throws CardException {
        if (response.getSW1() != 144) {
            throw new CardException("Unable to complete operation");
        }
    }
} // ends class

