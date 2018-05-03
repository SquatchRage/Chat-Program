
public class LoginValidationCheck implements Runnable
{
    Login login;
    String cmd;
    static final String CLOSE_DIALOG = "close";
    static final String USERNAME_TAKEN = "username taken";
    static final String INVALID_ID = "invalid id";
    
    public LoginValidationCheck(Login pwd)
    {
        login = pwd;
        cmd = "";
    }
    
    public void setAction(String action)
    {
        cmd = action;
    }
    
    public void run()
    {   
        if (cmd.equals(CLOSE_DIALOG))
            login.dispose();

        else if (cmd.equals(USERNAME_TAKEN))
            login.warningLabel.setText("Username already exists. Please choose a different one.");
            
        else if (cmd.equals(INVALID_ID))
            login.warningLabel.setText("Invalid username or password. Please try again.");
    }
}
