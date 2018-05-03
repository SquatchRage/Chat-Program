import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class ChatGUIUpdater implements Runnable
{
    FriendListDialog gui;
    static final String NONEXISTENT_USERNAME = "username does not exist";
    static final String NONEXISTENT_USERNAME_DELETE = "username does not exist";
    static final String BRQ_RECEIVED = "buddy request received";
    static final String BRQ_SENT = "Buddy request sent";
    static final String BUDDY_ACCEPTED = "Buddy accepted";
    static final String CHAT_REQUEST = "Recieved chat request";
    static final String CHAT_ACCEPTED = "chat accepted";
    static final String CHAT_DENIED = "chat denied";
    static final String MESSAGE_RECEIVED = "message received";
    static final String BUDDY_DELETED = "Buddy Deleted";
    static final String DELETE_REQUEST = "Delete request received";

    String cmd;
    String buddy;
    String message;
    
    public ChatGUIUpdater(FriendListDialog gui)
    {
        this.gui = gui;
        cmd = "";
        buddy = "";
        message = "";
                
    }
    
    public void setAction(String cmd)
    {
        this.cmd = cmd;
    }
    
    public void setBuddy(String b)
    {
        buddy = "";
        buddy = b;
    }
    
    public void setMessage(String m)
    {
        message = "";
        message = m;
    }
    
    public void run()
    {   
        if (cmd.equals(NONEXISTENT_USERNAME))
            gui.badAddUsername();
        
        else if (cmd.equals(BRQ_RECEIVED))
            gui.buddyRequestReceived(buddy);
        
        else if (cmd.equals(BUDDY_ACCEPTED))
            gui.addBuddy(buddy);
        
        else if (cmd.equals(CHAT_REQUEST))
            gui.displayChatRequest(buddy);
        
        else if (cmd.equals(CHAT_ACCEPTED))
            gui.chatAccepted(buddy);
        
        else if (cmd.equals(MESSAGE_RECEIVED))
            gui.displayMessage(buddy, message);
        
        else if (cmd.equals(BUDDY_DELETED))
        	gui.removeBuddy(buddy);
        
        else if (cmd.equals(DELETE_REQUEST))
        	gui.buddyDeletionReceived(buddy);
        
        else if (cmd.equals(NONEXISTENT_USERNAME_DELETE))
        	gui.badDeleteUsername();
    }
}
