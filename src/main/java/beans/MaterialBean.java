package beans;

import profiles.MaterialProfile;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import dao.MaterialDAO;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import profiles.MaterialProfile;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class MaterialBean implements Serializable{
    private List<MaterialProfile> materials;
    private MaterialProfile selectedMaterial;
    //    private LazyDataModel<MaterialProfile> lazyModel;
    
    @ManagedProperty("#{materialDAO}")
    private  MaterialDAO profile;
    

    @PostConstruct
    public void init(){
        materials = profile.getMaterials();
//        lazyModel = new LazyUserDataModel(profile.getUsers());
    }
    
//    public LazyDataModel<UserProfile> getLazyModel(){
//        return lazyModel;
//    }
    
    public void onRowEdit(RowEditEvent event) {
        profile.editMaterial(selectedMaterial);
        FacesMessage msg = new FacesMessage("Данные сохранены","Пользователь "+selectedMaterial.getMatname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Редактирование отменено","Пользователь "+selectedMaterial.getMatname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(MaterialDAO profile) {
        this.profile = profile;
    }

    /**
     * @return the selectedMaterial
     */
    public MaterialProfile getSelectedMaterial() {
        return selectedMaterial;
    }

    /**
     * @param selectedMaterial the selectedMaterial to set
     */
    public void setSelectedMaterial(MaterialProfile selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }
    
    //    public LazyDataModel<MaterialProfile> getLazyModel() throws SQLException,NamingException {
//        lazyModel = new LazySteelDataModel(profile.getMaterials());
//        return lazyModel;
//    }

    /**
     * @return the materials
     */
    public List<MaterialProfile> getMaterials() {
        return materials;
    }
    
    public void saveMaterial(){
        profile.saveMaterial();
        setMaterials(profile.getMaterials());
        
    }
    
    public void deleteMaterials(){
        profile.deleteMaterial(selectedMaterial);
        setMaterials(profile.getMaterials());
        FacesMessage msg = new FacesMessage("Удаление пользователя","Пользователь "+selectedMaterial.getMatname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        
    }

    /**
     * @param materials the materials to set
     */
    public void setMaterials(List<MaterialProfile> materials) {
        this.materials = materials;
    }
    

    
   
    
    
    
}
