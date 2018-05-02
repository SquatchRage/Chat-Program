import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class Chatbox extends JFrame implements ActionListener, DocumentListener
{
    String chattingWith;
    String  user;
    
    CTS cts;
    
    JLabel friendLabel;
    
    JPanel buttonPanel;
    JPanel fieldPanel;
    JPanel backPanel;
    JTextArea upperTextArea,
              lowerTextArea;
    
    JScrollPane sp;
    JScrollPane sp1;
    JButton sendButton;
    Container cp;

    
    public Chatbox(String chattingWith, String user, CTS cts)
    {
        this.chattingWith = chattingWith;
        this.user = user;
        this.cts = cts;
        
        friendLabel = new JLabel("Chatting with " + chattingWith);
        
        upperTextArea = new JTextArea(8,50);
        upperTextArea.setEditable(false);
        upperTextArea.setLineWrap(true);
        sp = new JScrollPane(upperTextArea);
        
        
        lowerTextArea = new JTextArea(8, 50);
        lowerTextArea.setLineWrap(true);
        lowerTextArea.getDocument().addDocumentListener(this);
        sp1 = new JScrollPane(lowerTextArea);
        
        sendButton = new JButton("SEND");
        sendButton.setEnabled(false);
        sendButton.setActionCommand("SEND");
        sendButton.addActionListener(this);
        
        
        fieldPanel = new JPanel(new GridBagLayout()); 
        backPanel = new JPanel(new FlowLayout());
      //  backPanel.add(fieldPanel);
   	 	buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
   	 	buttonPanel.add(sendButton);
   	 
   	 GridBagConstraints gbc = new GridBagConstraints();
   	 
   		gbc.insets = new Insets(4, 4, 4, 4);

   		gbc.gridx = 0;
   		gbc.gridy = 0;
   		gbc.weightx = 1;
   		fieldPanel.add(upperTextArea, gbc);

   		gbc.gridx = 0;
   		gbc.gridy = 2;
   		gbc.weightx = 1;
   		fieldPanel.add(lowerTextArea, gbc);
   		

   	 cp = getContentPane();
   	 cp.add(fieldPanel, BorderLayout.CENTER);
   	 cp.add(buttonPanel, BorderLayout.SOUTH);
   	 
      
        
        setUp();
    }

    public void setUp()
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();

        setSize(d.width/3, d.height/3);
        
        setLocation(d.width/3, d.height/3);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     
        setTitle("Chat with " + chattingWith);
        
        setVisible(true);   
    }

    public void actionPerformed(ActionEvent e) 
    {
        if (e.getActionCommand().equals("SEND"))
        {
            upperTextArea.append(user + "| " + lowerTextArea.getText() + "\n");
            cts.send("CHAT TO|" + chattingWith + "|" + user + "|" + lowerTextArea.getText() + "\n");
            lowerTextArea.setText("");
        }   
        
    }

    public void insertUpdate(DocumentEvent e) 
    {
        if(lowerTextArea.getText().trim().length() < 1)
            sendButton.setEnabled(false);
        else
            sendButton.setEnabled(true);
        
    }

    public void removeUpdate(DocumentEvent e) 
    {
        if(lowerTextArea.getText().trim().length() < 1)
            sendButton.setEnabled(false);
        else
            sendButton.setEnabled(true);
        
    }

    public void changedUpdate(DocumentEvent e) {};
    
    public static void main(String args[]){
    	
    	new Chatbox(null, null, null);
    }
}