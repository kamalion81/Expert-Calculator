import java.io.Serializable;
import java.sql.*;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ViewScoped;
import javax.sql.DataSource;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class LoginController implements Serializable{
    
    @Resource(name = "jdbc/usersdb")
    private DataSource ds;
    
    
    private String login;
    private String password;
    
    
    public String verifyUser(){
        try {
            Connection dbConnection = ds.getConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM javadb.users");
 
                // выполнить SQL запрос
        while(result.next()){
            System.out.println(result.getString("worker"));
        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return "done";
        
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
