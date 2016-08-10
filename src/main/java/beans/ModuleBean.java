package beans;

import profiles.ModuleProfile;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import dao.ModuleDAO;
import javax.annotation.PostConstruct;
import org.primefaces.event.RowEditEvent;
import profiles.MaterialProfile;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class ModuleBean implements Serializable {

    private List<ModuleProfile> values;
    private ModuleProfile selectedValue;

    @ManagedProperty("#{moduleDAO}")
    private ModuleDAO profile;

    @PostConstruct
    public void init() {
        setValues(profile.getValues());
//        lazyModel = new LazyUserDataModel(profile.getUsers());
    }

    public void onRowEdit(RowEditEvent event) {
        profile.editValue(getSelectedValue());
        FacesMessage msg = new FacesMessage("Данные сохранены", "Значение " + getSelectedValue().getValue());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Редактирование отменено", "Значение " + getSelectedValue().getValue());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the materials
     */
    public void saveValue() {
        profile.saveValue();
        setValues(profile.getValues());

    }

    public void deleteValue() {
        profile.deleteValue(getSelectedValue());
        setValues(profile.getValues());
        FacesMessage msg = new FacesMessage("Удаление пользователя", "Пользователь " + selectedValue.getValue());
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(ModuleDAO profile) {
        this.profile = profile;
    }

    /**
     * @return the values
     */
    public List<ModuleProfile> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<ModuleProfile> values) {
        this.values = values;
    }

    /**
     * @return the selectedValue
     */
    public ModuleProfile getSelectedValue() {
        return selectedValue;
    }

    /**
     * @param selectedValue the selectedValue to set
     */
    public void setSelectedValue(ModuleProfile selectedValue) {
        this.selectedValue = selectedValue;
    }

}
