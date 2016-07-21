package beans;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import dao.*;
import java.sql.SQLException;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@ViewScoped
public class UsersBean implements Serializable{
    private LazyDataModel<UserProfile> lazyModel;
    
    @ManagedProperty("#{userDAO}")
    private UserDAO profile;
    

    /**
     * @return the lazyModel
     */
//    @PostConstruct
//    public void init() throws SQLException {
//        lazyModel = new LazyUserDataModel(profile.getUsers());
//    }
    
    public LazyDataModel<UserProfile> getLazyModel() throws SQLException,NamingException {
        lazyModel = new LazyUserDataModel(profile.getUsers());
        return lazyModel;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(UserDAO profile) {
        this.profile = profile;
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Данные сохранены","sdsdsdsd");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Редактирование отменено","fgfgftr");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Пользователь изменен", "Предыдущее: " + oldValue + ", Новое:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
   
    
    public void saveUser(){
        
        
    }
    
    
}
    
//   private DataSource ds;
//   private String name;
//   private String role;
//   
//   public UsersBean() throws NamingException{
//       InitialContext initContext = new InitialContext();
//        this.ds = (DataSource) initContext.lookup("java:comp/env/jdbc/usersdb");
//    
//}
//   
//   public ArrayList<UsersBean> getUsers(){
//       return UserDAO.getUser();
//      Connection conn = getDs().getConnection() throw SQLException;
//      try {
//         Statement stmt = conn.createStatement();        
//         ResultSet result = stmt.executeQuery("SELECT name, role FROM calculator.users");  
//         
////          return ResultSupport.toResult(result);
//         CachedRowSet crs = new com.sun.rowset.CachedRowSetImpl();         
//            // or use an implementation from your database vendor
//         crs.populate(result);
//         return crs;
//      } finally {
//         conn.close();
//      }
//   }
//
//    /**
//     * @return the ds
//     */
//    public DataSource getDs() {
//        return ds;
//    }
//}
