package beans;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import dao.KoefDAO;
import javax.annotation.PostConstruct;
import org.primefaces.event.RowEditEvent;
import profiles.KoefProfile;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class KoefBean implements Serializable{
    private List<KoefProfile> values;
    private KoefProfile selectedValue;
    
    @ManagedProperty("#{koefDAO}")
    private  KoefDAO profile;

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
    public List<KoefProfile> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<KoefProfile> values) {
        this.values = values;
    }

    /**
     * @return the selectedValue
     */
    public KoefProfile getSelectedValue() {
        return selectedValue;
    }

    /**
     * @param selectedValue the selectedValue to set
     */
    public void setSelectedValue(KoefProfile selectedValue) {
        this.selectedValue = selectedValue;
    }

    /**
     * @param profile
     */
    public void setProfile(KoefDAO profile) {
        this.profile = profile;
    }
    
    public <T> T getProf(T prof){
        return prof;
    }

    /**
     * @return the profile
     */
    public KoefDAO getProfile() {
        return profile;
    }

    
    


    

    
   
    
    
    
}
