package dao;

import profiles.ModuleProfile;
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
public class ModuleDAO implements Serializable {

    private String matgroup;
    private int temp;
    private float value;
    private DataSource ds;

    public ModuleDAO() {

        DataBase db = new DataBase();
        ds = db.getDs();

    }

    public ArrayList<ModuleProfile> getValues() {

        ArrayList<ModuleProfile> values = new ArrayList<>();
        try {
            try (Connection conn = ds.getConnection()) {
                PreparedStatement stat = conn.prepareStatement("SELECT modul_prodolnoi_uprugosti.id, modul_prodolnoi_uprugosti.temp, "
                        + "modul_prodolnoi_uprugosti.value, material_group.groupname "
                        + "FROM calculator.modul_prodolnoi_uprugosti INNER JOIN calculator.material_group ON modul_prodolnoi_uprugosti.material_group_id = material_group.id");

                ResultSet result = stat.executeQuery();
                while (result.next()) {
                    ModuleProfile profile = new ModuleProfile(result.getInt("id"), result.getString("groupname"),result.getInt("temp"), result.getInt("value"));
                    values.add(profile);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return values;
    }

    public List<String> getGroups() {

        List<String> groups = new ArrayList<>();
        try {
            try (Connection conn = ds.getConnection()) {
                Statement stat = conn.createStatement();
                ResultSet result = stat.executeQuery("SELECT groupname FROM calculator.material_group");

                while (result.next()) {
                    String group = result.getString("groupname");
                    groups.add(group);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ModuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;

    }

    public void saveValue() {
        try {
            try (Connection conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {

                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.material_group WHERE groupname = ?");
                    pstat.setString(1, getMatgroup());
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int group = res.getInt("id");
                    String query = "INSERT INTO calculator.modul_prodolnoi_uprugosti (material_group_id ,temp ,value) VALUES "
                            + "(" + group + "," + temp + ","+ value +")";
                    PreparedStatement stat2 = conn.prepareStatement(query);

                    stat2.executeUpdate();

                    FacesMessage message = null;
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Данные сохранены", "Значение " + value + " добавлен");
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
            Logger.getLogger(ModuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editValue(ModuleProfile selectedValue) {

        try {
            try (Connection conn = ds.getConnection()) {
                conn.setAutoCommit(false);
                boolean committed = false;

                try {
                    PreparedStatement pstat = conn.prepareStatement("SELECT id FROM calculator.material_group WHERE groupname = ?");
                    pstat.setString(1, selectedValue.getMatgroup());
                    ResultSet res = pstat.executeQuery();

                    res.next();
                    int group = res.getInt("id");
                    Statement stat = conn.createStatement();
                    String querry = "UPDATE `calculator`.`modul_prodolnoi_uprugosti`\n"
                            + "SET\n"
                            + "`material_group_id` = "+group+",\n"
                            + "`temp` = "+selectedValue.getTemp()+",\n"
                            + "`value` = "+selectedValue.getValue()+"\n"
                            + "WHERE `id` = "+selectedValue.getId()+";";
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
            Logger.getLogger(ModuleDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void deleteValue(ModuleProfile selectedValue) {

        try {
            try (Connection conn = ds.getConnection()) {

                Statement stat = conn.createStatement();

                stat.executeUpdate("DELETE FROM `calculator`.`modul_prodolnoi_uprugosti` WHERE `id`='" + selectedValue.getId() + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModuleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the matgroup
     */
    public String getMatgroup() {
        return matgroup;
    }

    /**
     * @param matgroup the matgroup to set
     */
    public void setMatgroup(String matgroup) {
        this.matgroup = matgroup;
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



}
