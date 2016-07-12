
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class CalcWizard implements Serializable {

    private Calc calc = new Calc();

    private boolean skip;

    public Calc getCalc() {
        return calc;
    }
    
    public void setCalc(Calc calc) {
        this.calc = calc;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

}
    
