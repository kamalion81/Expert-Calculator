package dao;

import profiles.SteelProfile;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;


/**
 *
 * @author Kamalion
 */
@ManagedBean
@ApplicationScoped
public class SteelDAO implements Serializable {
    
    private DataBase data;
    private String mark;
    private int sigma20; 
    private int sigmaT;
    private Connection dbConnection;
    
    public SteelDAO() throws NamingException, SQLException {
        
        data = new DataBase();

    }

    public ArrayList<SteelProfile> getSteels() throws NamingException, SQLException {

        ArrayList<SteelProfile> steels = new ArrayList<>();
        Connection conn = data.getdbConnection();
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM calculator.steels");

        ResultSet result = stat.executeQuery();
        while (result.next()) {
            SteelProfile profile = new SteelProfile(result.getInt("id"), result.getString("mark"), result.getInt("sigma20"), result.getInt("sigmaT"));
            steels.add(profile);
        }

//        dbConnection.close();
        return steels;
    }
    
    public void saveMark() throws SQLException {
        dbConnection = data.getdbConnection();
        try {

            String query = "INSERT INTO `calculator`.`steels` (`mark`, `sigma20`, `sigmaT`) VALUES "
                    + "('" + mark + "','" + sigma20 + "','" + sigmaT + "')";
            PreparedStatement stat2 = dbConnection.prepareStatement(query);

            stat2.executeUpdate();

            FacesMessage message = null;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Данные сохранены", "Марка стали " + mark + " добавлена");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } finally {
            dbConnection.close();
        }
    }
    
    public void deleteSteels(){
    
}

    /**
     * @return the mark
     */
    public String getMark() {
        return mark;
    }

    /**
     * @param mark the mark to set
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * @return the sigma20
     */
    public int getSigma20() {
        return sigma20;
    }

    /**
     * @param sigma20 the sigma20 to set
     */
    public void setSigma20(int sigma20) {
        this.sigma20 = sigma20;
    }

    /**
     * @return the sigmaT
     */
    public int getSigmaT() {
        return sigmaT;
    }

    /**
     * @param sigmaT the sigmaT to set
     */
    public void setSigmaT(int sigmaT) {
        this.sigmaT = sigmaT;
    }

}
