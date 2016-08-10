package dao;

import profiles.KoefProfile;
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
public class KoefDAO implements Serializable {

    private String matname;
    private int temp_min;
    private int temp_max;
    private float value;
    private DataSource ds;

    public KoefDAO() {

        DataBase db = new DataBase();
        ds = db.getDs();

    }

    public ArrayList<KoefProfile> getValues() {

        ArrayList<KoefProfile> values = new ArrayList<>();
        try {
            try (Connection conn = getDs().getConnection()) {
                PreparedStatement stat = conn.prepareStatement("SELECT koeficient_lineinogo_rashireniya.id, materials.matname, "
                        + "koeficient_lineinogo_rashireniya.temp_min, koeficient_lineinogo_rashireniya.temp_max, "
                        + "koeficient_lineinogo_rashireniya.value "
                        + "FROM calculator.koeficient_lineinogo_rashireniya INNER JOIN calculator.materials ON koeficient_lineinogo_rashireniya.material_id = materials.id");

                ResultSet result = stat.executeQuery();
                while (result.next()) {
                    KoefProfile profile = new KoefProfile(result.getInt("id"), result.getString("matname"), result.getInt("temp_min"), 
                            result.getInt("temp_max"),result.getFloat("value"));
                    values.add(profile);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(KoefDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(KoefDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                    String query = "INSERT INTO `calculator`.`koeficient_lineinogo_rashireniya`\n" +
                                                                "(`material_id`,\n" +
                                                                "`temp_min`,\n" +
                                                                "`temp_max`,\n" +
                                                                "`value`)\n" +
                                                                "VALUES\n" +
                                                                "("+ material_id +",\n" +
                                                                temp_min +",\n" +
                                                                temp_max +",\n" +
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
            Logger.getLogger(KoefDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editValue(KoefProfile selectedValue) {

        try {
            try (Connection conn = getDs().getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {
                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.materials WHERE matname = ?");
                    pstat.setString(1, selectedValue.getMatname());
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int material = res.getInt("id");
                    Statement stat = conn.createStatement();
                    String querry = "UPDATE `calculator`.`koeficient_lineinogo_rashireniya` "
                            + "SET `material_id`='" + material + "', `temp_min`='" + selectedValue.getTemp_min()+ "',"
                            + "`temp_max`='" + selectedValue.getTemp_max() + "',"
                            + "`value`='" + selectedValue.getValue() + "' WHERE `id`='" + selectedValue.getId() + "';";
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
            Logger.getLogger(KoefDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void deleteValue(KoefProfile selectedValue) {

        try {
            try (Connection conn = getDs().getConnection()) {

                Statement stat = conn.createStatement();

                stat.executeUpdate("DELETE FROM `calculator`.`koeficient_lineinogo_rashireniya` WHERE `id`='" + selectedValue.getId() + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(KoefDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * @return the temp_min
     */
    public int getTemp_min() {
        return temp_min;
    }

    /**
     * @param temp_min the temp_min to set
     */
    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    /**
     * @return the temp_max
     */
    public int getTemp_max() {
        return temp_max;
    }

    /**
     * @param temp_max the temp_max to set
     */
    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

}
