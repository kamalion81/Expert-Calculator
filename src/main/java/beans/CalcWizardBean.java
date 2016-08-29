package beans;


import dao.CalcDAO;
import dao.MaterialDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;
import javax.validation.constraints.Min;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ViewScoped
public class CalcWizardBean implements Serializable {
    
    
    private boolean skip;
    
    @ManagedProperty("#{calcDAO}")
    private  CalcDAO calc;
    
    @ManagedProperty("#{materialDAO}")
    private  MaterialDAO mat;

    public CalcDAO getCalc() {
        
        if(!(calc.getId_open()==null)&!skip){
            calc.fillCalc();
            skip = true;
            
        }
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

    /**
     * @return the mat
     */
    public MaterialDAO getMat() {
        return mat;
    }

    /**
     * @param mat the mat to set
     */
    public void setMat(MaterialDAO mat) {
        this.mat = mat;
    }


}
    
