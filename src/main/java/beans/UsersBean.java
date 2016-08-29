package beans;

import profiles.UserProfile;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import dao.UserDAO;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@ViewScoped
public class UsersBean implements Serializable{
//    private LazyDataModel<UserProfile> lazyModel;
    private List<UserProfile> users;
    private UserProfile selectedUser;
    private String currentPass;
    
    @ManagedProperty("#{userDAO}")
    private UserDAO profile;
    

    @PostConstruct
    public void init() {
        users = profile.getUsers();
        selectedUser = users.get(0);
        
//        lazyModel = new LazyUserDataModel(profile.getUsers());
    }
    
//    public LazyDataModel<UserProfile> getLazyModel(){
//        return lazyModel;
//    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(UserDAO profile) {
        this.profile = profile;
    }
    
    public void onRowEdit(RowEditEvent event) {
        profile.editUser(selectedUser);
        FacesMessage msg = new FacesMessage("Данные сохранены","Пользователь "+selectedUser.getUsername());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void editUser() {
        profile.editUser(selectedUser);
        FacesMessage msg = new FacesMessage("Данные сохранены","Пользователь "+selectedUser.getUsername());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Редактирование отменено","Пользователь "+selectedUser.getUsername());
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

    /**
     * @return the selectedUser
     */
    public UserProfile getSelectedUser() {
        return selectedUser;
    }

    /**
     * @param selectedUser the selectedUser to set
     */
    public void setSelectedUser(UserProfile selectedUser) {
        this.selectedUser = selectedUser;
    }
    

    /**
     * @return the users
     */
    public List<UserProfile> getUsers() {
        return users;
    }
    
    public void deleteUsers(){
        profile.deleteUser(selectedUser);
        setUsers(profile.getUsers());
        FacesMessage msg = new FacesMessage("Удаление пользователя","Пользователь "+selectedUser.getUsername());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        
    }
    
    public void saveUser(){
        profile.saveUser();
        setUsers(profile.getUsers());
        
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<UserProfile> users) {
        this.users = users;
    }
    
    public String getCurrentPass(){
        currentPass =  profile.getPassDt(selectedUser);
        return currentPass;
    }
    
    public void savePass(){
        profile.savePass(selectedUser);
        setUsers(profile.getUsers());
    }

    
}
    
