import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class LoginController implements Serializable{
    
    private String login;
    private String password;
    
    
    public String verifyUser(){
        return "CalculationWizard";
        
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
