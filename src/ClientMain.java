import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


public class ClientMain
{

    public static void main(String[] args) 
    {        
        try
        {
            Socket s = new Socket("127.0.0.1", 12345); // connect to server (me)
            Talker t = new Talker(s, "Client"); // create talker from socket
            new Login(t);
            
        } 
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            new JOptionPane();
            JOptionPane.showMessageDialog(null, "The server is not currently available.");
            return;
        }

    }

}
