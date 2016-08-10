package dao;

import profiles.Napr_Tekuch_Soprotiv_Profile;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

/**
 *
 * @author Kamalion
 */
@ManagedBean
@ApplicationScoped
public class DopuskNaprDAO implements Serializable {

    private String matname;
    private int thickness_min;
    private int thickness_max;
    private int temp;
    private float value;
    private DataSource ds;

    public DopuskNaprDAO() {

        DataBase db = new DataBase();
        ds = db.getDs();

    }

    public ArrayList<Napr_Tekuch_Soprotiv_Profile> getValues() {

        ArrayList<Napr_Tekuch_Soprotiv_Profile> values = new ArrayList<>();
        try {
            try (Connection conn = getDs().getConnection()) {
                PreparedStatement stat = conn.prepareStatement("SELECT dopuskaemoe_napryazhenie.id, materials.matname, "
                        + "dopuskaemoe_napryazhenie.thickness_min, dopuskaemoe_napryazhenie.thickness_max, dopuskaemoe_napryazhenie.temp,"
                        + " dopuskaemoe_napryazhenie.value "
                        + "FROM calculator.dopuskaemoe_napryazhenie INNER JOIN calculator.materials ON dopuskaemoe_napryazhenie.material_id = materials.id");

                ResultSet result = stat.executeQuery();
                while (result.next()) {
                    Napr_Tekuch_Soprotiv_Profile profile = new Napr_Tekuch_Soprotiv_Profile(result.getInt("id"), result.getString("matname"), result.getInt("thickness_min"), 
                            result.getInt("thickness_max"), result.getInt("temp"), result.getFloat("value"));
                    values.add(profile);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DopuskNaprDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return values;
    }

    public List<String> getMaterials() {

        List<String> materials = new ArrayList<>();
        try {
            try (Connection conn = getDs().getConnection()) {
                Statement stat = conn.createStatement();
                ResultSet result = stat.executeQuery("SELECT matname FROM calculator.materials");

                while (result.next()) {
                    String material = result.getString("matname");
                    materials.add(material);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DopuskNaprDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return materials;

    }

    public void saveValue() {
        try {
            try (Connection conn = getDs().getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {

                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.materials WHERE matname = ?");
                    pstat.setString(1, matname);
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int material_id = res.getInt("id");
                    String query = "INSERT INTO `calculator`.`dopuskaemoe_napryazhenie`\n" +
                                                                "(`material_id`,\n" +
                                                                "`thickness_min`,\n" +
                                                                "`thickness_max`,\n" +
                                                                "`temp`,\n" +
                                                                "`value`)\n" +
                                                                "VALUES\n" +
                                                                "("+ material_id +",\n" +
                                                                thickness_min +",\n" +
                                                                thickness_max +",\n" +
                                                                temp +",\n" +
                                                                value+");";
                    PreparedStatement stat2 = conn.prepareStatement(query);

                    stat2.executeUpdate();

                    FacesMessage message = null;
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Данные сохранены", "Значение напряжения " + value + " добавлено");
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    conn.commit();
                    committed = true;
                } finally {
                    if (!committed) {
                        conn.rollback();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DopuskNaprDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editValue(Napr_Tekuch_Soprotiv_Profile selectedValue) {

        try {
            try (Connection conn = getDs().getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {
                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.materials WHERE matname = ?");
                    pstat.setString(1, selectedValue.getMaterial());
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int material = res.getInt("id");
                    Statement stat = conn.createStatement();
                    String querry = "UPDATE `calculator`.`dopuskaemoe_napryazhenie` "
                            + "SET `material_id`='" + material + "', `thickness_min`='" + selectedValue.getThickness_min() + "',"
                            + "`thickness_max`='" + selectedValue.getThickness_max() + "',"
                            + "`temp`='" + selectedValue.getTemp()+ "', `value`='" + selectedValue.getValue() + "' WHERE `id`='" + selectedValue.getId() + "';";
                    stat.executeUpdate(querry);

                    conn.commit();
                    committed = true;
                } finally {
                    if (!committed) {
                        conn.rollback();
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DopuskNaprDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void deleteValue(Napr_Tekuch_Soprotiv_Profile selectedValue) {

        try {
            try (Connection conn = getDs().getConnection()) {

                Statement stat = conn.createStatement();

                stat.executeUpdate("DELETE FROM `calculator`.`dopuskaemoe_napryazhenie` WHERE `id`='" + selectedValue.getId() + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DopuskNaprDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the matname
     */
    public String getMatname() {
        return matname;
    }

    /**
     * @param matname the matname to set
     */
    public void setMatname(String matname) {
        this.matname = matname;
    }

    /**
     * @return the thickness_min
     */
    public int getThickness_min() {
        return thickness_min;
    }

    /**
     * @param thickness_min the thickness_min to set
     */
    public void setThickness_min(int thickness_min) {
        this.thickness_min = thickness_min;
    }

    /**
     * @return the thickness_max
     */
    public int getThickness_max() {
        return thickness_max;
    }

    /**
     * @param thickness_max the thickness_max to set
     */
    public void setThickness_max(int thickness_max) {
        this.thickness_max = thickness_max;
    }

    /**
     * @return the temp
     */
    public int getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(int temp) {
        this.temp = temp;
    }

    /**
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     * @return the ds
     */
    public DataSource getDs() {
        return ds;
    }

    /**
     * @param ds the ds to set
     */
    public void setDs(DataSource ds) {
        this.ds = ds;
    }

}
