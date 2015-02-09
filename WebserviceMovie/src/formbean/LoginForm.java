package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;



public class LoginForm extends FormBean{
    private String username;
    private String password;
    
    public String getUsername()  { return username; }
    public String getPassword()   { return password; }
    
	public void setUsername(String s) { username = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s) {	password = trimAndConvert(s, "<>\"");                }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (username == null || username.trim().length() == 0) errors.add("Username is required");
        if (password == null || password.length() == 0) errors.add("Password is required");
        
        return errors;
    }
}
