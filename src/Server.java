import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server
{
    ServerSocket     serverSocket;
    Socket           regularSocket;
    Talker           newUser;
    String           userID;
    File userListFile;
    DataInputStream dis;
    DataOutputStream dos;
    int numberOfUsers = 0;
    CTC tempCTC;    
    HashTable userList;
    int count = 0;
    
    Server()
    {
    	// creates connection, hashtables and loads userList
        try{
        serverSocket = new ServerSocket(12345);
        userList = new HashTable();                                    
        userList.load("userlist.dat");
        
        // constantly accpets incoming and counts number of users (on client side) that have logged on
        while(true)
        {
            System.out.println("No. of clients since startup: " + count);
            regularSocket  = serverSocket.accept(); 
            count++;
            userID = new String("User " + count);
            tempCTC = new CTC(new Talker(regularSocket, userID), userID, this);
                
        }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}


