import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FriendListDialog extends JFrame implements ActionListener, ListSelectionListener
{
    JLabel friendLabel;
    JMenu menu;
    JMenuBar menuBar;
    JMenuItem item, item1, item2;
    JList<BuddyObject> buddyList;
    DefaultListModel<BuddyObject> dlm;
    JPanel buttonPanel;
    JButton chatButton;
    JScrollPane sp;
    String username;
    CTS cts;
    DialogUpdater updater;
    Hashtable<String, Chatbox> ht;
    Container cp;

    public FriendListDialog(CTS cts, String username)
    {
        this.cts = cts;
        
        this.username = username;
        
    
        menu = new JMenu("Options");
        menuBar = new JMenuBar();
        menuBar.add(menu);
        item = new JMenuItem("Add Friend");
        item.setActionCommand("ADD BUDDY");
        item.addActionListener(this);
        menu.add(item);

        item1 = new JMenuItem("Delete Friend");
        item1.setActionCommand("DELETE BUDDY");
        item1.addActionListener(this);
        menu.add(item1);

        item2 = new JMenuItem("Log Out");
        menu.add(item2);
        setJMenuBar(menuBar);

        ht = new Hashtable<String, Chatbox>();
        
        friendLabel = new JLabel("My Buddies");
        
        dlm = new DefaultListModel<BuddyObject>();
        buddyList = new JList<BuddyObject>(dlm);
        buddyList.addListSelectionListener(this);
        sp = new JScrollPane(buddyList);
        
        buttonPanel = new JPanel(new GridLayout(3,1));
        
        chatButton = new JButton("Chat");
        chatButton.setActionCommand("START CHAT");
        chatButton.addActionListener(this);
        chatButton.setEnabled(false);
        buttonPanel.add(chatButton);
        
        cp = getContentPane();
        cp.add(sp, BorderLayout.CENTER);
       // cp.add(friendLabel, BorderLayout.NORTH);
        cp.add(buttonPanel, BorderLayout.SOUTH);
        
        setUp();
        
    }
    
  
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("ADD BUDDY"))
            updater = new DialogUpdater(new AddFriendDialog(cts, username));
        
        else if (e.getActionCommand().equals("START CHAT"))
        {
            ht.put(dlm.elementAt(buddyList.getSelectedIndex()).username,
                   new Chatbox(dlm.elementAt(buddyList.getSelectedIndex()).username, username, cts));
            cts.send("CHAT REQUEST TO|" + dlm.elementAt(buddyList.getSelectedIndex()).username + "|" + username );
        }
        
        else if (e.getActionCommand().equals("DELETE BUDDY")){
            updater = new DialogUpdater(new DeleteFriendDialog(cts, username));

        	
        }

    }
    
    public void buddyRequestReceived(String buddy)
    {
        new JOptionPane();
        int accepted = JOptionPane.showConfirmDialog(this, "Buddy Request from " + buddy + ".\n"
                                                             + "Do you want to accept this request?");
        
        if (accepted == JOptionPane.YES_OPTION)
        {
        	cts.send("BUDDY ACCEPTED|" + buddy + "|" + username);
            this.addBuddy(buddy);
        }
    }
    //delete buddy
    public void buddyDeletionReceived(String buddy)
    {
        new JOptionPane();
        int accepted = JOptionPane.showConfirmDialog(this, "Do you want to delete" + buddy + ".\n");
                                                            
        
        if (accepted == JOptionPane.YES_OPTION)
        {
        	cts.send("BUDDY DELETED|" + buddy + "|" + username);
            this.removeBuddy(buddy);
        }
    }
    
    public void badAddUsername()
    {
        new JOptionPane();
        JOptionPane.showMessageDialog(this, "Error: Username does not exist.");
        
        updater = new DialogUpdater(new AddFriendDialog(cts, username));
    }
    
    public void badDeleteUsername()
    {
        new JOptionPane();
        JOptionPane.showMessageDialog(this, "Error: Username does not exist.");
        
        updater = new DialogUpdater(new DeleteFriendDialog(cts, username));
    }
    
    public void addBuddy(String buddy)
    {
        BuddyObject newBud = new BuddyObject(buddy, true);
        dlm.addElement(newBud);
    }
    
    public void removeBuddy(String buddy){
    	BuddyObject oldBud = new BuddyObject(buddy, true);
    	dlm.removeElement(oldBud);
    	
    }

    public void valueChanged(ListSelectionEvent e) 
    {
        if(buddyList.getSelectedIndex() >= 0)
        {
            item2.setEnabled(true);
            chatButton.setEnabled(true);
        }
        
        else
        {
            item2.setEnabled(false);
            chatButton.setEnabled(false); 
        }
    }
    
    public void displayChatRequest(String buddy)
    {
        new JOptionPane();
        int decision = JOptionPane.showConfirmDialog(this, buddy + " has requested to chat with you. Do you accept?");
        
        if (decision == JOptionPane.YES_OPTION)
        {
            ht.put(buddy, new Chatbox(buddy, username, cts));
            cts.send("CHAT REQUEST ACCEPTED|" + username + "|" + buddy);
        }
        
        else
        	cts.send("CHAT REQUEST DENIED|" + username + "|" + buddy);
        
    }
    
    public void chatAccepted(String buddy)
    {
        ht.get(buddy).upperTextArea.append(buddy + " has accepted your chat request.\n");
    }
    
    public void chatDenied(String buddy)
    {
        ht.get(buddy).upperTextArea.append(buddy + " has denied your chat request.\n");
    }
    
    public void displayMessage(String buddy, String message)
    {
        ht.get(buddy).upperTextArea.append(buddy + ": " + message + "\n");
    }
    
 /*   public static void main(String args[]){
    	
    	new FriendListDialog(null, null);
    }*/
    
    public void setUp()
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();

        setSize(d.width/8, d.height/3);
        
        setLocation(d.width/3, d.height/3);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             
        setVisible(true);   
    }
}
