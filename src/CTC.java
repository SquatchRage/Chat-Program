import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;


public class CTC implements Runnable
{
    String  message;
	Server server;
	Thread ctcThread;
    Talker talker;
    String clientID;
  

    CTC(Talker t, String ID, Server s)
    {
         talker = t;
         clientID = ID;
         server = s;
         
         if(s == null){
        	 
        	 throw new RuntimeException("Server null inside CTC contruct.");
         }
         
         Thread ctcThread = new Thread(this);
         ctcThread.start();
    }
    
    public void run()
    {
        while(true)
        {
            try{
            message = this.receive();

            }
            catch(SocketException se)
            {
                System.out.println("Socket exception thrown.");
            }
            if(message != null)
            {                
                if (message.startsWith("LOGOUT"))
                {
                    System.out.println(clientID + " successfully logged out");
                    server.userList.remove(clientID); 
                }

                
                else if (message.startsWith("REGISTER"))
                {
                    System.out.println("User attempting to register");
                    register();         
                }
                
                else if (message.startsWith("LOGIN"))
                {
                    System.out.println("User attempting to login");
                    userLogin();
                }
                
                else if (message.startsWith("BUDDY REQUEST TO"))
                {
                    processFriendRequest(message);
                }
                
                else if (message.startsWith("BUDDY ACCEPTED"))
                {
                    friendAdded(message);
                }
                
                else if (message.startsWith("DELETE BUDDY"))
                {
                    friendDeleted(message);
                }
                
                else if (message.startsWith("CHAT REQUEST TO"))
                {
                    forwardChatRequest(message);
                }
                
                else if (message.startsWith("CHAT REQUEST ACCEPTED"))
                {
                    chatRequestAccepted(message);
                }
                
                else if (message.startsWith("CHAT REQUEST DENIED"))
                {
                    chatRequestDenied(message);
                }
                
                else if (message.startsWith("CHAT TO"))
                {
                    forwardMessage(message);
                }
            }
        }
    }
    
    public void send(String message)
    {
        try 
        {
            talker.send(message);
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
            message = talker.receive();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        return message;
    }
    
    public void setUserID(String newID)
    {
        talker.setUserID(newID); // change name in talker for debugging
        clientID = newID;
    }
    
    public String getUserID()
    {
        return clientID;
    }
    
    //Message delimiter is |
    private boolean userLogin()
    {
        boolean successfulLogin = false;
        
        String[] messageContents = message.split("[\\|]");
        
        if(server.userList.containsKey(messageContents[1]))
        {
        	User temp = server.userList.get(messageContents[1]);

            String userpw = server.userList.get(messageContents[1]).password;
                // get the password from the intended user
            
            if (userpw.equals(messageContents[2]))
            {
                successfulLogin = true;
                temp.userCTC = this;
                this.send("SUCCESSFUL LOGIN|" + messageContents[1]);
                
                for(int i = 0; i < temp.friendList.size(); i++){
             	   
             	   this.send("FRIEND LIST| " + temp.friendList.get(i));
                }
            }
            
            else
                this.send("INVALID ID");
        }
        
        else
            this.send("INVALID ID");
        
        return successfulLogin;
    }
    
    private void register()
    {
        boolean successfulReg = false;
        
        String[] messageContents = message.split("[\\|]");
        
        System.out.println("Username: " + messageContents[1] + " Password: " + messageContents[2]);
        
        /*construct a new user using the ctc (online status), username and pw
        change the anon ctc to the user's ctc
        add the new User to the hashtable
        if the username is not found in the hashtable,
        then the username can be created.*/


        if (!server.userList.containsKey(messageContents[1])){
        
            
            User user = new User(this, messageContents[1], messageContents[2]);
            
            this.setUserID(messageContents[1]); 
            
            server.userList.put(user.username, user);
            
            server.userList.store("userlist.dat");
            
            successfulReg = true;
            
        }
        
        if(successfulReg)
        {
            System.out.println("User registration successful!");
            this.send("SUCCESSFUL REGISTRATION|" + messageContents[1]);
        }
        else
        {
            System.out.println("Registration failed, username" +
                               " already exists.");
            this.send("USERNAME TAKEN");
        }
    }
    
    public void processFriendRequest(String message)
    {
        String[] messageContents = message.split("[\\|]");
            // the format is always "buddy request to|destination|sender"
        
        if (!server.userList.containsKey(messageContents[1]))
            this.send("BRQ FAILED USER DOES NOT EXIST");
        
        else
        {
            User temp = server.userList.get(messageContents[1]);
            if (temp.isOnline())
                temp.userCTC.send("BUDDY REQUEST FROM|" + messageContents[2]);
            else
            {
                temp.enqueueCommand("BUDDY REQUEST FROM|" + messageContents[2]);
                server.userList.store("userlist.dat");
            }
        }
    }
    
    public void processDeleteRequest(String message)
    {
        String[] messageContents = message.split("[\\|]");
        
        if (!server.userList.containsKey(messageContents[1]))
            this.send("DELETE FAILED USER DOES NOT EXIST");
        
      
    }
    
    public void friendAdded(String msg)
    {
        String[] messageContents = msg.split("[\\|]");
            // format is "BUDDY ACCEPTED|senderofreq|receiverofreq"
        
        server.userList.get(messageContents[1]).addBuddy(messageContents[2]);
        server.userList.get(messageContents[2]).addBuddy(messageContents[1]);
        
        //if(server.userList.get(messageContents[1]).isOnline())
             server.userList.get(messageContents[1]).userCTC.send("BUDDY ACCEPTED|" + messageContents[2]);
             // If the user who sent the request is online, let them know
        //else
            //server.userList.get(messageContents[1]).enqueueCommand("BUDDY ACCEPTED|" + messageContents[2]);
            // Otherwise, enqueue their request accepted message 
        
        server.userList.store("userlist.dat");   
    }
    
    public void friendDeleted(String msg)
    {
        String[] messageContents = msg.split("[\\|]");
            // format is "BUDDY DELETED|senderofreq|receiverofreq"
        
        server.userList.get(messageContents[2]).removeBuddy(messageContents[1]);
        server.userList.get(messageContents[1]).removeBuddy(messageContents[2]);

        server.userList.store("userlist.dat");   
    }
    
    // dying here, but why?-----------------Works on registration, but fails if user logins in a 2nd time. Issue w/List in User?
    public void forwardChatRequest(String msg)
    {   
    	String[] messageContents;
    	if(msg == null){
    		
    		throw new IllegalArgumentException("CTC.forwardChatRequest(): msg cannot be null");
    	}
    	User temp;
    	messageContents = msg.split("[\\|]");
            // format is "CHAT REQUEST TO|recipient|sender
    	if(server.userList.containsKey(messageContents[1].trim()))
    		System.out.println(messageContents[1] + " im 1   " + messageContents[2] + " im 2" );
    	else
    		System.out.println("I am user list" +server.userList);
        
        temp = server.userList.get(messageContents[1].trim());
        System.out.println("I am temp" + temp + "\n" + messageContents[1]);
        
        CTC ctc = temp.userCTC;
        //server.userList.get(messageContents[1]).userCTC.send("CHAT REQUEST FROM|" + messageContents[2]); 
        System.out.println("I am ctc" + ctc);

        ctc.send("CHAT REQUEST FROM|" + messageContents[2]); 
    }
    
    public void chatRequestAccepted(String msg)
    {
        String[] messageContents = msg.split("[\\|]");
            // format is "CHAT REQUEST ACCEPTED|OneWhoAccepted|OneWhoSentReq
        
        server.userList.get(messageContents[2]).userCTC.send("CHAT ACCEPTED|" + messageContents[1]);
            // send the okay to the person who sent the request
        
    }
    
    public void chatRequestDenied(String msg)
    {
        String[] messageContents = msg.split("[\\|]");
        
        server.userList.get(messageContents[2]).userCTC.send("CHAT DENIED|" + messageContents[1]);
        
    }
    
    public void forwardMessage(String msg)
    {
        String[] messageContents = msg.split("[\\|]");
        // format is "CHAT TO|Destination|Sender|ActualMessage
    
        server.userList.get(messageContents[1]).userCTC.send("CHAT FROM|" + messageContents[2] +
                                                             "|" + messageContents[3]);
        // send the message to the user with the source
    }
}
