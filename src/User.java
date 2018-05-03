import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;


public class User
{
    String    username;
    String    password;
    UserQueue commandQueue;
    CTC userCTC;
    Vector<String> friendList;
    File userFile;
    DataOutputStream dos;
    
    // constructor will be used only during registration,
    // so we can assume that the user is online immediately
    public User(CTC ctc, String userName, String passWord)
    {
        username = userName;
        password = passWord;
        userCTC = ctc;
        friendList = new Vector<String>();
        commandQueue = new UserQueue();
    }
    
    // add friend to list
    public void addBuddy(String buddy)
    {
        friendList.add(buddy);
    }
    
    //remove friend from list -- seems to be only working when the server is closed and re-opened
    public void removeBuddy(String buddy)
    {
        friendList.remove(buddy);
        
    }
    
    //store friends
    public String saveList()
    {       
        String userContents = "";
        userContents = userContents + username + '#' + password + '#';
        
        userContents = userContents + friendList.size() + '#';
        
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
