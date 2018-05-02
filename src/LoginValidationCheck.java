
public class LoginValidationCheck implements Runnable
{
    Login login;
    String whatDoIDo;
    static final String CLOSE_DIALOG = "close";
    static final String USERNAME_TAKEN = "username taken";
    static final String INVALID_ID = "invalid id";
    
    public LoginValidationCheck(Login pwd)
    {
        login = pwd;
        whatDoIDo = "";
    }
    
    public void setAction(String action)
    {
        whatDoIDo = action;
    }
    
    public void run()
    {   
        if (whatDoIDo.equals(CLOSE_DIALOG))
            login.dispose();

        else if (whatDoIDo.equals(USERNAME_TAKEN))
            login.warningLabel.setText("Username already exists. Please choose a different one.");
            
        else if (whatDoIDo.equals(INVALID_ID))
            login.warningLabel.setText("Invalid username or password. Please try again.");
    }
}
