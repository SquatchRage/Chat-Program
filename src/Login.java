import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Login extends JDialog implements ActionListener, DocumentListener
{
    CTS myCTS;
    
    JTextField usernameTextField;
    JTextField passwordTextField;
    
    JButton    registerButton,   loginButton, cancelButton;
    
    JPanel  fieldPanel;
    JPanel  buttonPanel;
    Container cp;
    JLabel  usernameLabel;
    JLabel  passwordLabel;
    JLabel  warningLabel;

    
    Login(Talker t)
    {
    	myCTS = new CTS(t, new LoginValidationCheck(this));
    	    
         registerButton = new JButton("Register");
         loginButton = new JButton("Login");
         cancelButton = new JButton("Cancel");
         
         registerButton.setActionCommand("REGISTER");
         loginButton.setActionCommand("LOGIN");
         cancelButton.setActionCommand("CANCEL");
         
         registerButton.addActionListener(this);
         loginButton.addActionListener(this);
         cancelButton.addActionListener(this);
         
         registerButton.setEnabled(false);
         loginButton.setEnabled(false);
         
         usernameTextField = new JTextField(30);
         passwordTextField   = new JTextField(30);
     
         usernameTextField.getDocument().addDocumentListener(this);
         passwordTextField.getDocument().addDocumentListener(this);
     
     
         buttonPanel = new JPanel();
         
         warningLabel = new JLabel ();
         usernameLabel = new JLabel("Username- ");
         passwordLabel   = new JLabel("Password- ");
         
         //puts focus in usernameField
         addWindowListener( new WindowAdapter() {
        	    public void windowOpened( WindowEvent e ){
        	        usernameTextField.requestFocus();
        	    }
        	}); 
         
    	 fieldPanel = new JPanel(new GridBagLayout()); 
    	 buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    	 
    	 // Layout
    	 GridBagConstraints gbc = new GridBagConstraints();
    	 
    		gbc.insets = new Insets(4, 4, 4, 4);

    		gbc.gridx = 0;
    		gbc.gridy = 0;
    		gbc.weightx = 1;
    		fieldPanel.add(usernameLabel, gbc);

    		gbc.gridx = 1;
    		gbc.gridy = 0;
    		gbc.weightx = 1;
    		fieldPanel.add(usernameTextField, gbc);

    		gbc.gridx = 0;
    		gbc.gridy = 1;
    		gbc.weightx = 1;
    		fieldPanel.add(passwordLabel, gbc);

    		gbc.gridx = 1;
    		gbc.gridy = 1;
    		gbc.weightx = 1;
    		fieldPanel.add(passwordTextField, gbc);
    		
    		gbc.gridx = 1;
    		gbc.gridy = 2;
    		gbc.weightx = 1;
    		fieldPanel.add(warningLabel, gbc);
    		
    		buttonPanel.add(registerButton);
    		buttonPanel.add(loginButton);
    		buttonPanel.add(cancelButton);
         
     // Add Buttons to Panels
         buttonPanel.add(registerButton);
         buttonPanel.add(loginButton);
         buttonPanel.add(cancelButton);
         
    	 cp = getContentPane();
    	 cp.add(fieldPanel, BorderLayout.WEST);
    	 cp.add(buttonPanel, BorderLayout.SOUTH);

         
     	setSize(500, 300);
 		setTitle("Login");
 		setLocationRelativeTo(null);
 		setVisible(true);
         
        
        this.setModal(true);
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getActionCommand().equals("LOGIN"))
            myCTS.send("LOGIN|" + usernameTextField.getText() + "|" + passwordTextField.getText()); // FIX PASSWORD LATER, THIS WORKS TO GET STARTED
                
        else if (ae.getActionCommand().equals("REGISTER"))
            myCTS.send("REGISTER|" + usernameTextField.getText() + "|" + passwordTextField.getText());
            
        else if (ae.getActionCommand().equals("EXIT"))
        {
            this.dispose();
            return;
        }
    }
    
    public void insertUpdate(DocumentEvent de)
    {
        if (usernameTextField.getText().trim().length() < 3 || 
        		passwordTextField.getText().trim().length() < 3)
        {
            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
        }
        
        else
        {   
            if (usernameTextField.getText().contains("#")
             || usernameTextField.getText().contains("|")
             || usernameTextField.getText().contains("%"))
                   {
                        warningLabel.setText("Invalid symbol. Please refrain from using"        
                                         +  " special symbols in your username.");
                        
                        registerButton.setEnabled(false);
                        loginButton.setEnabled(false);    
                   }
                    
             else
             {
                    loginButton.setEnabled(true);
                    registerButton.setEnabled(true);
             }
        }
        

    }
    public void removeUpdate(DocumentEvent de)
    {
        if (usernameTextField.getText().trim().length() < 3 || 
        		passwordTextField.getText().trim().length() < 3)
        {
            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
        }
        
        else
        {   
            if (usernameTextField.getText().contains("#")
             || usernameTextField.getText().contains("|")
             || usernameTextField.getText().contains("%"))
                   {
                        warningLabel.setText("Invalid symbol. Please refrain from using"        
                                         +  " special symbols in your username.");
                        
                        registerButton.setEnabled(false);
                        loginButton.setEnabled(false);    
                   }
                    
             else
             {
                    loginButton.setEnabled(true);
                    registerButton.setEnabled(true);
             }
        }
        

    }
    
    public void changedUpdate(DocumentEvent de)
    {}
   
}
