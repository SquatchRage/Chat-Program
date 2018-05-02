


public class DialogUpdater implements Runnable
{
    AddFriendDialog afd;
    DeleteFriendDialog dfd;
    String command;
    String statusLabel;
    
    static final String NONEXISTENT_USERNAME = "username does not exist";
    static final String BRQ_SENT = "Buddy request sent";
    static final String DELETE = "Buddy deleted";
    
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
    
        else if (command.equals(BRQ_SENT))
        {
            afd.dispose();
        }
        
        else if (command.equals(DELETE))
        {
            afd.message.setText(statusLabel);
        }
    }
}
