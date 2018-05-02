/*import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ClientGUI extends JFrame implements ActionListener, DocumentListener
{
    JButton    sendButton,
               exitButton;
    
    JLabel     messageLabel;
    
    JPanel     GUIPanel;
    
    JTextField messageTF;

    Container cp;
    
    ConnectionToServer cts;
    
    Thread ctsThread;
    
    ClientGUI(Talker t)
    {
        cts = new ConnectionToServer(t, this); // create cts with talker and gui
        ctsThread = new Thread(cts);
        
        cp = getContentPane();
        
        sendButton = new JButton("SEND");
        sendButton.setActionCommand("SEND");
        sendButton.addActionListener(this);
        sendButton.setEnabled(false);
        
        exitButton = new JButton("EXIT");
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(this);
        
        messageTF = new JTextField(35);
        messageTF.getDocument().addDocumentListener(this);
        messageTF.setEnabled(true);
        
        messageLabel = new JLabel();
        
        GUIPanel = new JPanel();
        
        GUIPanel.add(messageLabel);
        GUIPanel.add(messageTF);
        GUIPanel.add(sendButton);
        GUIPanel.add(exitButton);
        
        cp.add(GUIPanel);
        
        setupMainFrame();
        
        ctsThread.start();
    }

    private void setupMainFrame()
    {
        Toolkit tk;
        Dimension d;
        
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        
        setSize(d.width/3, d.height/3);
        setLocation(d.width/3, d.height/3);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //program terminates when closed
        
        setTitle("ShoutOut 1.0");

        setVisible(true);                                    //Now we can see the window.
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getActionCommand().equals("SEND"))
            cts.send(messageTF.getText());
        
        else if (ae.getActionCommand().equals("EXIT"))
        {
            cts.send("LOGOUT");
            this.dispose();
            return;
        }
    }
    
    public void insertUpdate(DocumentEvent de)
    {
        if (messageTF.getText().trim().length() == 0)
            sendButton.setEnabled(false);
        
        else
            sendButton.setEnabled(true);
    }
    public void removeUpdate(DocumentEvent de)
    {
        if (messageTF.getText().trim().length() == 0)
            sendButton.setEnabled(false);
        
        else
            sendButton.setEnabled(true);
    }
    public void changedUpdate(DocumentEvent de)
    {}
}
*/