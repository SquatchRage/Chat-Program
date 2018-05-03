


public class DialogUpdater implements Runnable
{
    AddFriendDialog afd;
    DeleteFriendDialog dfd;
    String command;
    String statusLabel;
    
    static final String NONEXISTENT_USERNAME = "username does not exist";
    static final String BRQ_SENT = "Buddy request sent";
    static final String NONEXISTANT_BUDDY = "No buddy by that name";
    
    public DialogUpdater(AddFriendDialog afd)
    {
        this.afd = afd;
        statusLabel = "";
        command = "";
    }
    
    public DialogUpdater(DeleteFriendDialog dfd)
    {
        this.dfd = dfd;
        statusLabel = "";
        command = "";
    }
    
    public void setAction(String cmd)
    {
        command = cmd;
    }
    
    public void run()
    {
        if (command.equals(NONEXISTENT_USERNAME))
        {
            afd.message.setText(statusLabel);

        }
        
        else if (command.equals(NONEXISTANT_BUDDY))
        {
            dfd.message.setText(statusLabel);
        }
    
        else if (command.equals(BRQ_SENT))
        {
            afd.dispose();
        }
        
    
    }
}
