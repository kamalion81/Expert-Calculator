package beans;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import dao.DopuskNaprDAO;
import javax.annotation.PostConstruct;
import org.primefaces.event.RowEditEvent;
import profiles.Napr_Tekuch_Soprotiv_Profile;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class DopuskNaprBean implements Serializable{
    private List<Napr_Tekuch_Soprotiv_Profile> values;
    private Napr_Tekuch_Soprotiv_Profile selectedValue;
    
    @ManagedProperty("#{dopuskNaprDAO}")
    private  DopuskNaprDAO profile;

    @PostConstruct
    public void init(){
        values = getProfile().getValues();
    }
    
    public void onRowEdit(RowEditEvent event) {
        getProf(getProfile()).editValue(getSelectedValue());
        FacesMessage msg = new FacesMessage("Данные сохранены","Значение "+selectedValue.getValue());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Редактирование отменено","Значение "+selectedValue.getValue());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void saveValue(){
        getProf(getProfile()).saveValue();
        setValues(getProf(getProfile()).getValues());
        
    }
    
    public void deleteValues(){
        getProf(getProfile()).deleteValue(selectedValue);
        setValues(getProf(getProfile()).getValues());
        FacesMessage msg = new FacesMessage("Удаление значения","Значение "+selectedValue.getValue());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the values
     */
    public List<Napr_Tekuch_Soprotiv_Profile> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<Napr_Tekuch_Soprotiv_Profile> values) {
        this.values = values;
    }

    /**
     * @return the selectedValue
     */
    public Napr_Tekuch_Soprotiv_Profile getSelectedValue() {
        return selectedValue;
    }

    /**
     * @param selectedValue the selectedValue to set
     */
    public void setSelectedValue(Napr_Tekuch_Soprotiv_Profile selectedValue) {
        this.selectedValue = selectedValue;
    }

    /**
     * @param profile
     */
    public void setProfile(DopuskNaprDAO profile) {
        this.profile = profile;
    }
    
    public <T> T getProf(T prof){
        return prof;
    }

    /**
     * @return the profile
     */
    public DopuskNaprDAO getProfile() {
        return profile;
    }

    
    


    

    
   
    
    
    
}
