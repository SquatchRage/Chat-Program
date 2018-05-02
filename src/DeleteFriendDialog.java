import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class DeleteFriendDialog extends JDialog implements ActionListener, DocumentListener
{
    JLabel     message;
    JTextField friendNameTextfield;
    JButton    deleteRequestButton;
    JButton    cancelButton;
    JPanel     textFieldPanel;
    JPanel      buttonPanel;
    CTS cts;
    Container cp;
    String myUsername;
    
    public DeleteFriendDialog(CTS cts, String myUsername)
    {
        this.cts = cts;
        this.myUsername = myUsername;
        
        message = new JLabel("Enter the name of the friend you to delete!");
        
        friendNameTextfield = new JTextField(25);
        friendNameTextfield.getDocument().addDocumentListener(this);
        textFieldPanel = new JPanel();
        textFieldPanel.add(friendNameTextfield);        
        
        deleteRequestButton = new JButton("DELETE");
        deleteRequestButton.setActionCommand("DELETE");
        deleteRequestButton.addActionListener(this);
        deleteRequestButton.setEnabled(false);
        
        cancelButton = new JButton("CANCEL");
        cancelButton.setActionCommand("CANCEL");
        cancelButton.addActionListener(this);
        
        buttonPanel = new JPanel();
        buttonPanel.add(deleteRequestButton);
        buttonPanel.add(cancelButton);
        
        this.add(message, BorderLayout.NORTH);
        this.add(textFieldPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
   
        setUp();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("DELETE"))
            {

                    cts.send("DELETE BUDDY|" + friendNameTextfield.getText()
                            + "|" + myUsername);
                    dispose();
                }
            
        
        else if (e.getActionCommand().equals("CANCEL"))
        {
            this.dispose();
        }
    }

    public void insertUpdate(DocumentEvent e) 
    {        
        if (friendNameTextfield.getText().trim().length() < 3)
            deleteRequestButton.setEnabled(false);
        else
            deleteRequestButton.setEnabled(true);
    }

    public void removeUpdate(DocumentEvent e) 
    {
        if (friendNameTextfield.getText().trim().length() < 3)
            deleteRequestButton.setEnabled(false);
        else
            deleteRequestButton.setEnabled(true);        
    }

    public void changedUpdate(DocumentEvent e) {};
    
    public void setUp(){  
        Toolkit tk;
        Dimension d;
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        
        setSize(d.width/7, d.height/8);
        setLocation(d.width/3, d.height/3);
        
        this.setModal(true);
        this.setVisible(true);     }
    
    public static void main(String args[]){
    	new AddFriendDialog(null, null);
    }
    
 
}
