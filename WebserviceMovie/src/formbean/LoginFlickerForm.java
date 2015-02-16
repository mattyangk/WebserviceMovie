package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LoginFlickerForm extends FormBean{
    private String oauth_token;
    private String oauth_verifier;
    
    public String getOauth_token()  { return oauth_token; }
    public String getOauth_verifier()   { return oauth_verifier; }
    
	public void setOauth_token(String s) { oauth_token = trimAndConvert(s,"<>\"");  }
	public void setOauth_verifier(String s) {	oauth_verifier = trimAndConvert(s, "<>\"");                }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (oauth_token == null || oauth_token.trim().length() == 0) errors.add("oauth_token is required");
        if (oauth_verifier == null || oauth_verifier.length() == 0) errors.add("oauth_verifier is required");
        
        return errors;
    }
}
