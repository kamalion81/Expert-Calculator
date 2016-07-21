
package dao;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;


/**
 *
 * @author Kamalion
 */
@ManagedBean
@ApplicationScoped
public class UserDAO implements Serializable{
    
    private String username;
    private String rolename;
    private String pass;
    private String pass_again;
    private DataSource ds;
    private Connection dbConnection;
    private boolean loggedIn;
     
     public UserDAO() throws NamingException,SQLException{
         
         InitialContext initContext = new InitialContext();
         ds = (DataSource) initContext.lookup("java:comp/env/jdbc/usersdb");
         
     }
     
     
     private Statement getStatement() throws NamingException,SQLException{
         
         if (ds == null) {
            throw new SQLException("No data source");
        }
        dbConnection = ds.getConnection();
        if(dbConnection==null) throw new SQLException("No connection");
         
         Statement statement = dbConnection.createStatement();
         
         return statement;
         
     }
     
     public  ArrayList<UserProfile> getUsers() throws NamingException,SQLException {
         
        ArrayList<UserProfile> users = new ArrayList<>();
        ResultSet result = getStatement().executeQuery("SELECT users.id, users.username, roles.rolename FROM calculator.users\n"
                + "INNER JOIN calculator.roles ON users.id_role = roles.id");
        while (result.next()) {
            UserProfile profile = new UserProfile(result.getString("username"), result.getString("rolename"), result.getInt("id"));
            users.add(profile);
        }
        dbConnection.close();
        return users;
        
    }

     public List<String> getRoles() throws NamingException,SQLException{
         
         ArrayList<String> roles = new ArrayList<>();
         ResultSet result = getStatement().executeQuery("SELECT rolename FROM calculator.roles");
         while (result.next()) {
                String role = result.getString("rolename");
                roles.add(role);
         }
         dbConnection.close();
         return roles;
         
     }
     
public String login(){
         
        FacesMessage message = null;

        try {
            doLogin();
        } catch (SQLException e) {
            return "internalError";
        }
        if (loggedIn) {
            return "loginSuccess";
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка авторизации", "Неверное имя или пароль");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
       
    }
         

public void doLogin() throws SQLException {  
      if (ds == null) throw new SQLException("No data source");      
      Connection conn = ds.getConnection();
      if (conn == null) throw new SQLException("No connection");      
      
      try {
         conn.setAutoCommit(false);
         boolean committed = false;
         try {
            PreparedStatement passwordQuery = conn.prepareStatement(
               "SELECT pass from calculator.users WHERE username = ?");
            passwordQuery.setString(1,username);
         
            ResultSet result = passwordQuery.executeQuery();

            if (!result.next()) return;
            String storedPassword = result.getString("pass");                
            loggedIn = pass.equals(storedPassword);
            if (!loggedIn) return;
            
            
            conn.commit();
            committed = true;
         } finally {
            if (!committed) conn.rollback();
         }
      }
      finally {               
         conn.close();
      }
   }

public void saveUser() throws NamingException, SQLException{
    
    if(pass.equals(pass_again)) {
          if (ds == null)
            throw new SQLException("No data source");
        dbConnection = ds.getConnection();
        if (dbConnection == null) throw new SQLException("No connection"); 
            try {
                dbConnection.setAutoCommit(false);
                boolean committed = false;

                try {

                    PreparedStatement stat = dbConnection.prepareStatement("SELECT id FROM calculator.roles where rolename = ?");
                    stat.setString(1, rolename);
                    ResultSet res = stat.executeQuery();

                    res.next();
                    int role = res.getInt("id");
                    String query = "INSERT INTO `calculator`.`users` (`username`, `pass`, `id_role`) VALUES "
                            + "('"+username+"','"+pass+"','"+role+"')";
                    PreparedStatement stat2 = dbConnection.prepareStatement(query);

                    stat2.executeUpdate();
                    
                    FacesMessage message = null;
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Данные сохранены","Пользователь "+username+" добавлен");
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    dbConnection.commit();
                    committed = true;
                } finally {
                    if (!committed) {
                        dbConnection.rollback();
                    }
                }
            } finally {
                dbConnection.close();
            }
        } else {
            FacesMessage message = null;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Несовпадение пароля", "Введенные пароли в полях отличаются");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }


    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * @param rolename the rolename to set
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the pass_again
     */
    public String getPass_again() {
        return pass_again;
    }

    /**
     * @param pass_again the pass_again to set
     */
    public void setPass_again(String pass_again) {
        this.pass_again = pass_again;
    }
     
    
    
    
}
