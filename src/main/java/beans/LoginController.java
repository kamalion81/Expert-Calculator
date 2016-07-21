import java.io.Serializable;
import java.sql.*;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ViewScoped;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class LoginController implements Serializable{
    
//    @Resource(name = "jdbc/usersdb")
    private DataSource ds;
    private String login;
    private String password;
    
    public LoginController() throws NamingException {
        
        InitialContext initContext = new InitialContext();
        this.ds = (DataSource) initContext.lookup("java:comp/env/jdbc/usersdb");
            
        }
    
    public String verifyUser(){
        try {
            if(ds==null) throw new SQLException("No data source");
            Connection dbConnection = ds.getConnection();
            if(dbConnection==null) throw new SQLException("No connection");
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
