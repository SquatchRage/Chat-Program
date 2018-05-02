import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;


public class User
{
    String    username,
              password;
    
    UserQueue commandQueue;
    CTC userCTC;
    Vector<String> friendList;
    File userFile;
    DataOutputStream dos;
    
    public User(CTC ctc, String uid, String pw)
    {
        username = uid;
        password = pw;
        userCTC = ctc;
            // constructor will be used only during registration,
            // so we can assume that the user is online immediately
        
        friendList = new Vector<String>();
        commandQueue = new UserQueue();
    }
    
    public void addBuddy(String buddy)
    {
        friendList.add(buddy);
    }
    
    public void removeBuddy(String buddy)
    {
        friendList.remove(buddy);
        
    }
    
    public String store()
    {       
        String userContents = "";
        userContents = userContents + username + '#'
                                    + password + '#';
        
        userContents = userContents + friendList.size() + '#';
            // store the number of buddies for parsing later
        
        for (int i = 0; i < friendList.size(); i++)
            userContents = userContents + friendList.elementAt(i) + '#';
        
        for (int i = 0; i < commandQueue.getSize(); i++)
            userContents = userContents + commandQueue.dequeue() + '#';
        
        System.out.println(userContents);
        
        return userContents;
    }
    
    public void enqueueCommand(String cmd)
    {
        commandQueue.enqueue(cmd);
    }
    
    public boolean isOnline()
    {
        return (userCTC != null);
    }
}
