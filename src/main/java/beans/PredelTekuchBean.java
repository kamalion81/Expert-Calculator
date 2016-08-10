package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import dao.PredelTekuchDAO;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class PredelTekuchBean extends DopuskNaprBean implements Serializable {

    @ManagedProperty("#{predelTekuchDAO}")
    private PredelTekuchDAO profile;

    /**
     * @param profile the profile to set
     */
    public void setProfile(PredelTekuchDAO profile) {
        this.profile = profile;
    }

    /**
     * @return the profile
     */
    @Override
    public PredelTekuchDAO getProfile() {
        return profile;
    }

}
