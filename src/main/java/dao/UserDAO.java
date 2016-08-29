package dao;

import profiles.UserProfile;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UserDAO implements Serializable {

    private String username;
    private String username_new;
    private String rolename;
    private String rolename_new;
    private String pass;
    private String pass_again;
    private String oldPass;
    private String newPass;
    private String newPass_again;
    private boolean loggedIn;
    private UserProfile selectedUser;
    private DataSource ds;

    public UserDAO() {
        
        DataBase db = new DataBase();
        ds = db.getDs();
        
        

//        InitialContext initContext = null;
//        try {
//            initContext = new InitialContext();
//        } catch (NamingException ex) {
//            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            ds = (DataSource) initContext.lookup("java:comp/env/jdbc/calcdb");
//        } catch (NamingException ex) {
//            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if (ds == null) {
//            try {
//                throw new SQLException("No data source");
//            } catch (SQLException ex) {
//                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

    }

    public List<UserProfile> getUsers() {

        List<UserProfile> users = new ArrayList<>();
        try {
            try (Connection conn = ds.getConnection()) {
                Statement stat = conn.createStatement();
                ResultSet result = stat.executeQuery("SELECT users.id, users.username, roles.rolename FROM calculator.users INNER JOIN calculator.roles ON users.id_role = roles.id");
                while (result.next()) {
                    UserProfile profile = new UserProfile(result.getString("username"), result.getString("rolename"), result.getInt("id"));
                    users.add(profile);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;

    }

    public List<String> getRoles() {

        List<String> roles = new ArrayList<>();
        try {
            try (Connection conn = ds.getConnection()) {
                Statement stat = conn.createStatement();
                ResultSet result = stat.executeQuery("SELECT rolename FROM calculator.roles");

                while (result.next()) {
                    String role = result.getString("rolename");
                    roles.add(role);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;

    }

    public String login() {

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

        try {
            try (Connection conn = ds.getConnection()) {
                PreparedStatement passwordQuery = conn.prepareStatement("SELECT pass from calculator.users WHERE username = ?");

                passwordQuery.setString(1, username);

                ResultSet result = passwordQuery.executeQuery();

                if (!result.next()) {
                    return;
                }
                String storedPassword = result.getString("pass");
                loggedIn = pass.equals(storedPassword);
                if (!loggedIn) {
                    return;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveUser() {

        if (pass.equals(pass_again)) {
            try {
                try (Connection conn = ds.getConnection()) {
                    conn.setAutoCommit(false);
                    boolean committed = false;

                    try {

                        PreparedStatement stat = conn.prepareStatement("SELECT id FROM calculator.roles where rolename = ?");
                        stat.setString(1, rolename);
                        ResultSet res = stat.executeQuery();

                        res.next();
                        int role = res.getInt("id");
                        String query = "INSERT INTO `calculator`.`users` (`username`, `pass`, `id_role`) VALUES "
                                + "('" + username + "','" + pass + "','" + role + "')";
                        PreparedStatement stat2 = conn.prepareStatement(query);

                        stat2.executeUpdate();

                        FacesMessage message = null;
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Данные сохранены", "Пользователь " + username + " добавлен");
                        FacesContext.getCurrentInstance().addMessage(null, message);

                        conn.commit();
                        committed = true;
                    } finally {
                        if (!committed) {
                            conn.rollback();
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesMessage message = null;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Несовпадение пароля", "Введенные пароли в полях отличаются");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void editUser(UserProfile selectedUser) {

        try {
            try (Connection conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {
                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.roles where rolename = ?");
                    pstat.setString(1, selectedUser.getRolename());
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int role = res.getInt("id");
                    Statement stat = conn.createStatement();
                    String querry = "UPDATE `calculator`.`users` SET `username`='" + selectedUser.getUsername() + "', `id_role`='" + role + "' WHERE `id`='" + selectedUser.getId() + "';";
                    stat.executeUpdate(querry);

                    conn.commit();
                    committed = true;
                } finally {
                    if (!committed) {
                        conn.rollback();
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void deleteUser(UserProfile selectedUser) {

        try {
            try (Connection conn = ds.getConnection()) {
                
                Statement stat = conn.createStatement();
                
                stat.executeUpdate("DELETE FROM `calculator`.`users` WHERE `id`='" + selectedUser.getId() + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void savePass(UserProfile selectedUser) {

        try {
            try (Connection conn = ds.getConnection()) {

                conn.setAutoCommit(false);
                boolean committed = false;
                try {
                    PreparedStatement pstat = conn.prepareStatement("SELECT pass FROM calculator.users where id = ?");
                    pstat.setInt(1, selectedUser.getId());
                    ResultSet res = pstat.executeQuery();
                    res.next();

                    if (res.getString("pass").equals(oldPass)) {
                        if (newPass.equals(newPass_again)) {

                            String querry1 = "UPDATE `calculator`.`users` SET `pass`='" + newPass + "' WHERE `id`='" + selectedUser.getId() + "'";
                            Statement stat = conn.createStatement();
                            stat.executeUpdate(querry1);

                            FacesMessage message = null;
                            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Пароль изменен", "Пароль изменен");
                            FacesContext.getCurrentInstance().addMessage(null, message);
                        } else {
                            FacesMessage message = null;
                            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Введенные пароли не совпадают", "Повтор пароля совершен с ошибкой");
                            FacesContext.getCurrentInstance().addMessage(null, message);
                        }
                    } else {
                        FacesMessage message = null;
                        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Неверный пароль", "Введенный пароль не совпадает с текущим");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }

                    conn.commit();
                    committed = true;
                } finally {
                    if (!committed) {
                        conn.rollback();
                    }
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void setSelectedUser(UserProfile selectedUser) {

        this.selectedUser = selectedUser;

    }

    /**
     * @return the selectedUser
     */
    public UserProfile getSelectedUser() {
        return selectedUser;
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

    /**
     * @return the oldPass
     */
    public String getOldPass() {
        return oldPass;
    }

    /**
     * @param oldPass the oldPass to set
     */
    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    /**
     * @param selectedUser
     * @return the currentDtPass
     */
    public String getPassDt(UserProfile selectedUser) {

        String passDt = null;
        try {
            try (Connection conn = ds.getConnection()) {
                PreparedStatement passwordQuery = conn.prepareStatement("SELECT users.pass from calculator.users WHERE users.id = ?");

                passwordQuery.setInt(1, selectedUser.getId());

                ResultSet result = passwordQuery.executeQuery();
                result.next();

                passDt = result.getString("pass");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passDt;
    }

    /**
     * @return the newPass
     */
    public String getNewPass() {
        return newPass;
    }

    /**
     * @param newPass the newPass to set
     */
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    /**
     * @return the newPass_again
     */
    public String getNewPass_again() {
        return newPass_again;
    }

    /**
     * @param newPass_again the newPass_again to set
     */
    public void setNewPass_again(String newPass_again) {
        this.newPass_again = newPass_again;
    }

    /**
     * @return the rolename_new
     */
    public String getRolename_new() {
        return rolename_new;
    }

    /**
     * @param rolename_new the rolename_new to set
     */
    public void setRolename_new(String rolename_new) {
        this.rolename_new = rolename_new;
    }

    /**
     * @return the username_new
     */
    public String getUsername_new() {
        return username_new;
    }

    /**
     * @param username_new the username_new to set
     */
    public void setUsername_new(String username_new) {
        this.username_new = username_new;
    }

}
