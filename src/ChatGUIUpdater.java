import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class ChatGUIUpdater implements Runnable
{
    FriendListDialog gui;
    static final String NONEXISTENT_USERNAME = "username does not exist";
    static final String BRQ_RECEIVED = "buddy request received";
    static final String BRQ_SENT = "Buddy request sent";
    static final String BUDDY_ACCEPTED = "Buddy accepted";
    static final String CHAT_REQUEST = "Recieved chat request";
    static final String CHAT_ACCEPTED = "chat accepted";
    static final String CHAT_DENIED = "chat denied";
    static final String MESSAGE_RECEIVED = "message received";
    static final String BUDDY_DELETED = "Buddy Deleted";
    static final String DELETE_REQUEST = "Delete request received";

    String whatDoIDo;
    String buddy;
    String message;
    
    public ChatGUIUpdater(FriendListDialog gui)
    {
        this.gui = gui;
        whatDoIDo = "";
        buddy = "";
        message = "";
                
    }
    
    public void setAction(String cmd)
    {
        whatDoIDo = cmd;
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
        if (whatDoIDo.equals(NONEXISTENT_USERNAME))
            gui.badAddUsername();
        
        else if (whatDoIDo.equals(BRQ_RECEIVED))
            gui.buddyRequestReceived(buddy);
        
        else if (whatDoIDo.equals(BUDDY_ACCEPTED))
            gui.addBuddy(buddy);
        
        else if (whatDoIDo.equals(CHAT_REQUEST))
            gui.displayChatRequest(buddy);
        
        else if (whatDoIDo.equals(CHAT_ACCEPTED))
            gui.chatAccepted(buddy);
        
        else if (whatDoIDo.equals(MESSAGE_RECEIVED))
            gui.displayMessage(buddy, message);
        
        else if (whatDoIDo.equals(BUDDY_DELETED))
        	gui.removeBuddy(buddy);
        
        else if (whatDoIDo.equals(DELETE_REQUEST))
        	gui.buddyDeletionReceived(buddy);
    }
}
