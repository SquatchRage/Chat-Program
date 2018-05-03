
public class Buddy 
{
    String username;
    boolean isOnline;
    
    public Buddy(String username, boolean isOnline)
    {
        this.username = username;
        this.isOnline = isOnline;
    }

    public String getOnlineStatus()
    {
        if(isOnline)
            return "Online";
        else
            return "Offline";
    }
    
    public String toString()
    {
        return this.username;
    }
}
