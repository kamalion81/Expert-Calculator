package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import dao.VremSoprotivDAO;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class VremSoprotivBean extends DopuskNaprBean implements Serializable {

    @ManagedProperty("#{vremSoprotivDAO}")
    private VremSoprotivDAO profile;

    /**
     * @param profile the profile to set
     */
    public void setProfile(VremSoprotivDAO profile) {
        this.profile = profile;
    }

    /**
     * @return the profile
     */
    @Override
    public VremSoprotivDAO getProfile() {
        return profile;
    }

}
