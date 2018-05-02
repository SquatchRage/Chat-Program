import java.io.IOException;
import java.net.SocketException;

import javax.swing.SwingUtilities;

public class CTS implements Runnable
{
    Talker communicator;
    String labelMessage;
    Thread ctsThread;
    LoginValidationCheck pwdu;
    ChatGUIUpdater cgu;
    
    CTS(Talker t, LoginValidationCheck pwdu)
    {
        communicator = t;
        this.pwdu = pwdu;
        ctsThread = new Thread(this);
        ctsThread.start();            // Begin communication with server
    }
    
    public void run()
    {
        String msg;
        
        try{
        while(true)
        {
            
            msg = this.receive();
            
            if (msg.startsWith("SUCCESSFUL REGISTRATION") ||
                msg.startsWith("SUCCESSFUL LOGIN"))
            {
                String messageContents[] = msg.split("[\\x7C]");
                
                pwdu.setAction(LoginValidationCheck.CLOSE_DIALOG);
                SwingUtilities.invokeLater(pwdu);
                cgu = new ChatGUIUpdater(new FriendListDialog(this, messageContents[1]));
            }
            
            	else if(msg.startsWith("FRIEND LIST")){
            	
                String messageContents[] = msg.split("[\\-]");

            	cgu.gui.addBuddy(messageContents[1]);
            	
            	
            }
            
            else if (msg.startsWith("USERNAME TAKEN"))
            {
                pwdu.setAction(LoginValidationCheck.USERNAME_TAKEN);
                SwingUtilities.invokeLater(pwdu);
            }
            
            else if (msg.startsWith("INVALID ID"))
            {
                pwdu.setAction(LoginValidationCheck.INVALID_ID);
                SwingUtilities.invokeLater(pwdu);
            }
            
            else if (msg.startsWith("BRQ FAILED USER DOES NOT EXIST"))
            {
                cgu.setAction(ChatGUIUpdater.NONEXISTENT_USERNAME);
                SwingUtilities.invokeLater(cgu);
            }
            
            else if (msg.startsWith("BUDDY REQUEST FROM|"))
            {
                processBuddyRequest(msg, cgu);
            }
            
            else if (msg.startsWith("BUDDY ACCEPTED"))
            {
                addBuddy(msg, cgu);
            }
            
            else if (msg.startsWith("BUDDY DELETED"))
            {
                removeBuddy(msg, cgu);
            }
            
            else if (msg.startsWith("CHAT REQUEST FROM"))
            {
                requestForChat(msg, cgu);
            }
            
            else if (msg.startsWith("CHAT ACCEPTED"))
            {
                chatAccepted(msg, cgu);
            }
            
            else if (msg.startsWith("CHAT DENIED"))
            {
                chatDenied(msg, cgu);
            }
            
            else if (msg.startsWith("CHAT FROM"))
            {
                displayMessage(msg, cgu);
            }
                                        
        }
        }
        catch(Exception SocketException)
        {
            System.out.println("Connection with server terminated.");
        }
    }
    
    public void send(String message)
    {
        try 
        {
            communicator.send(message);
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    public String receive() throws SocketException
    {
        String message = null;
        try 
        {
            message = communicator.receive();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        return message;
    }
    
    public void processBuddyRequest(String msg, ChatGUIUpdater cgu)
    {
        String[] messageContents = msg.split("[\\x7c]");
        cgu.setAction(ChatGUIUpdater.BRQ_RECEIVED);
        cgu.setBuddy(messageContents[1]);
        SwingUtilities.invokeLater(cgu);
    }
    
    public void addBuddy(String msg, ChatGUIUpdater cgu)
    {
        String[] messageContents = msg.split("[\\x7c]");
        cgu.setBuddy(messageContents[1]);
        cgu.setAction(ChatGUIUpdater.BUDDY_ACCEPTED);
        SwingUtilities.invokeLater(cgu);
    }
    
    public void removeBuddy(String msg, ChatGUIUpdater cgu)
    {
        String[] messageContents = msg.split("[\\x7c]");
        cgu.setBuddy(messageContents[1]);
        cgu.setAction(ChatGUIUpdater.BUDDY_DELETED);
        SwingUtilities.invokeLater(cgu);
    }
    
    public void requestForChat(String msg, ChatGUIUpdater cgu)
    {
        String[] messageContents = msg.split("[\\x7c]");
        cgu.setBuddy(messageContents[1]);
        cgu.setAction(ChatGUIUpdater.CHAT_REQUEST);
        SwingUtilities.invokeLater(cgu);
    }
    
    public void chatAccepted(String msg, ChatGUIUpdater cgu)
    {
        String[] messageContents = msg.split("[\\x7c]");
        cgu.setBuddy(messageContents[1]);
        cgu.setAction(ChatGUIUpdater.CHAT_ACCEPTED);
        SwingUtilities.invokeLater(cgu);
    }
    
    public void chatDenied(String msg, ChatGUIUpdater cgu)
    {
        String[] messageContents = msg.split("[\\x7c]");
        cgu.setBuddy(messageContents[1]);
        cgu.setAction(ChatGUIUpdater.CHAT_DENIED);
        SwingUtilities.invokeLater(cgu);
    }
    
    public void displayMessage(String msg, ChatGUIUpdater cgu)
    {
        String[] messageContents = msg.split("[\\x7c]");
        cgu.setBuddy(messageContents[1]); // source comes first
        cgu.setMessage(messageContents[2]); // destination comes next
        cgu.setAction(ChatGUIUpdater.MESSAGE_RECEIVED);
        SwingUtilities.invokeLater(cgu);
    }
}
