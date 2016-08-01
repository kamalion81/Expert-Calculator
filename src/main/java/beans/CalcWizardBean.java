package beans;


import dao.CalcDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.naming.NamingException;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class CalcWizardBean implements Serializable {

    private CalcDAO calc = new CalcDAO();

    private boolean skip;

    public CalcDAO getCalc() {
        return calc;
    }
    
    public void setCalc(CalcDAO calc) {
        this.calc = calc;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

}
    
