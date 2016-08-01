package beans;

import profiles.CalcProfile;
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
public class CalcsBean implements Serializable {

    private LazyDataModel<CalcProfile> lazyModel;

    @ManagedProperty("#{calcDAO}")
    private CalcDAO profile;

    public LazyDataModel<CalcProfile> getLazyModel() throws SQLException, NamingException {
        lazyModel = new LazyCalcDataModel(profile.getCalcs());
        return lazyModel;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(CalcDAO profile) {
        this.profile = profile;
    }

}
