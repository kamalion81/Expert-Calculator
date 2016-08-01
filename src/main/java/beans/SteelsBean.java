package beans;

import profiles.SteelProfile;
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
import dao.*;
import java.sql.SQLException;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class SteelsBean implements Serializable{
    
    private LazyDataModel<SteelProfile> lazyModel;
    
    @ManagedProperty("#{steelDAO}")
    private SteelDAO profile;
    

    
    public LazyDataModel<SteelProfile> getLazyModel() throws SQLException,NamingException {
        lazyModel = new LazySteelDataModel(profile.getSteels());
        return lazyModel;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(SteelDAO profile) {
        this.profile = profile;
    }
    
   
    
    
    
}
